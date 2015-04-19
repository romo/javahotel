

# Prerequisites #

http://www.virtualbox.org/

brctl : http://www.linuxfoundation.org/collaborate/workgroups/networking/bridge

dhcp


# Introduction #


Using information below I was able to set up for networking two guest systems using Virtual Box software.

My purpose was to achieve the following goals:

  * Guest system has access to internet
  * Guest system has access to host machine and vice versa
  * Guest system is invisible from outside world, also does not use external DHCP server
  * Should run without problem also if host system is getting different ip and name server via DHCP

This link contains useful informations:

http://www.virtualbox.org/wiki/Advanced_Networking_Linux


# Details #

## Installing Virtual Box ##

There is no problem to install Virtual Box and guest systems. The main problem was to set up networking for guests installed.

## DHCP ##

The host system should have **dhcpd** daemon installed. It is not necessary to have this daemon running all the time but should be started before any Virtual Box guest system is launched.

It is necessary to have **dhcpd.conf** configuration file in /etc directory. Below is an example:

```
#
# DHCP Server Configuration file.
#   see /usr/share/doc/dhcp*/dhcpd.conf.sample  
#

ddns-update-style interim;
ignore client-updates;

subnet 10.1.1.0 netmask 255.255.255.0 {

# --- default gateway
	option routers			10.1.1.1;
	option subnet-mask		255.255.255.0;

	option domain-name		"domain.org";
	option domain-name-servers	192.168.1.1,192.168.1.254;

#	option ntp-servers		10.1.1.1;

	range dynamic-bootp 10.1.1.2 10.1.1.10;
	default-lease-time 21600;
	max-lease-time 43200;
}


```

**domain-name** and **domain-name-servers** parameter lines will be replaced by bash script with the current values taken from **/etc/resolv.conf** file.


## Networking ##

It is a good practice to have a special user to install and run Virtual Box. Let's name this user as **vbox**

The most important is a bash script pasted below. It creates environment for guest systems. This script is performing the following tasks:

  1. Creates a bridge in host system (**br0**)
  1. Creates two interfaces: **tap1** and **tap2**
  1. Sets routing for host system
  1. Enables IP forwarding
  1. Sets NAT rules for guest systems.
  1. changes access modes for CD device (necessary for guest systems to start)
  1. run **replace** command - this command reproduces **domain name** and **name server** from **/etc/resolv.conf** to **/etc/dhcpd.conf**
  1. starts **dhcpd** daemon in host system


```
#!/bin/sh

replace() {
 RESOLV=/etc/resolv.conf
 INDHCP=/etc/dhcpd.conf
 OUTDHCPD=/etc/dhcpd.conf
 TMP=/tmp/$$

 nameserver=
 domain=
 while read key value
  do
    if [ $key = "nameserver" ]; then nameserver=$value; fi
    if [ $key = "domain" ]; then domain=$value; fi
  done < $RESOLV
#echo $domain $nameserver

sed -s 's/^.*option domain-name\s.*$/option domain-name "'$domain'";/' $INDHCP >$TMP
sed -s 's/^.*option domain-name-servers.*$/option domain-name-servers'\ $nameserver';/' $TMP >$OUTDHCPD
}

PATH=/sbin:/usr/bin:/bin:/usr/bin:/usr/local/sbin


# create the bridge
brctl addbr br0

NUMBER_OF_VM=3
USER=vbox

NB=1
while [ $NB -lt $NUMBER_OF_VM ]
do
   VBoxTunctl -t tap$NB -u $USER
   ip link set up dev tap$NB
   brctl addif br0 tap$NB
   let NB=$NB+1
done


# set the IP address and routing
ip link set up dev br0
ip addr add 10.1.1.1/24 dev br0
ip route add 10.1.1.0/24 dev br0

INTIF="br0"
#EXTIF="eth0"
EXTIF="wlan0"


echo 1 > /proc/sys/net/ipv4/ip_forward


# set forwarding and nat rules
iptables -A FORWARD -i $EXTIF -o $INTIF -j ACCEPT
iptables -A FORWARD -i $INTIF -o $EXTIF -j ACCEPT
iptables -t nat -A POSTROUTING -o $EXTIF -j MASQUERADE

chmod a+rw /dev/scd0

replace

/etc/rc.d/init.d/dhcpd restart
```

Additional informations:

  * Script is configured for creating two interfaces. If more is needed than change NUMBER\_OF\_VM accordingly
  * **replace** function modifies _all_ domain\_name parameters in **/etc/dhcpd.conf** file. If dhcpd daemon is to handle more than one network then more sophisticated method should be implemented.

## Setting within guest systems ##

For Windows system it is enough to check "Obtain IP address automatically" and "Obtain DNS server address automatically" in IP configuration.

For Linux system also command:
```
  ip route add default via 10.1.1.1 dev eth0
```
should be executed. It could be added to **/etc/init.d/rc.local** file.


## How to use ##

Script should be run as **root** every time before any guest system is started.

Every guest system is given IP address from DHCP server. It can be reached from host system using this address.