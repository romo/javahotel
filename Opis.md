Ta strona opisuje ogólne wymagania i działanie system.



# Wstęp #

Poniższy opis określa wymaganie dotyczące systemu. **Wymagania** definiują jakie funkcje system ma realizować, zaś **Uzycie** określa w postaci tzw. Use Case/User Story (przypadki użycia/scenariusze) sytuacje, w jakich system ma funkcjonwać. **Różne uwagi** zawierają kilka założeń jakie powinien spełniać system.

# Różne uwagi #

Główną częścią programu z punktu widzenia użytkownika powinna być tabela (panel) rezerwacyjna. Zdecydowaną większość czasu użytkownik będzie spędzał mając wyświetloną tę tabelę, jest wskazane, aby cała podstawowa funkcjonalność programu była możliwa do wykonania poprzez tę tabelę. Pozostałe funkcje (rzadziej używane) nie powinny być eksponowane z głównego panelu.

Cenniki są raczej elementem planowania i nie powinny krępować użytkownika w trakcie sprzedaży. Użytkownik powinien mieć swobodę wyznaczania ceny.

Podział pokoi na standardy może także ulegać zmianie w trakcie sprzedaży. Np. pokój w standardzie LUX może byc sprzedany gościowi jaki zwykły STANDARD, dwójka jako jedynka itp itd.

# Wymagania #

## Administracja programem ##
Zakładanie baz danych (hoteli), użytkowników, praw dostępu, archiwizacja
Oferta (lista usług), cenniki
Tworzenie listy usług, cenników, rabatów
Uwaga: cenniki definiowane na początku sezonu są elementem planowania. Istotną cechą jest łatwość modyfikacji ceny w momencie rezerwacji czy sprzedaży.

## Rezerwacja ##
Prowadzenie rezerwacji miejsc hotelowych oraz usług dodatkowych.
Obsługa osób indywidualnych i grup
Obsługa „cyklu życiowego” rezerwacji: potwierdzona, niepotwierdzona, internetowa
Rezerwacja: wymagana zaliczka lub bez zaliczki. Sytuacja "rezerwacja bezkosztowa" (bez zaliczki), ale wymagająca potwierdzenia.

## Recepcja ##

Obsługa gościa lub grupy podczas pobytu w hotelu.
Zameldowanie (check-in), rejestracja dodatkowych usług, wymeldowanie (check-out).
Zbieranie informacji niezbędnych do rozliczenia pobyty gościa lub grupy.

## Sprzedaż ##
Rozliczenie gościa, grupy. Wystawienie rachunku, zaliczki, przedpłaty. Korekty, anulacje. Sprzedaż „ręczna”. Możliwość scalenia kilku rachunków w jeden oraz rozdzielenia jednego rachunku na kilka.


## Raporty, statystyki ##
Statystyka sprzedaży, kosztów, obłożenia itp. itd.
Mozliwość łatwego dodawania nowych raportów, specyficznych dla użytkownika

## Biuro rachunkowe ##
Emisja danych do biura rachunkowego

# Użycie #

# Scenariusze: Rezerwacja #

## Scen01, rezerwacja ##
**Klient** (indywidualny, firma) dzwoni, faksuje, mejluje i podaje co chce zarezerować: ile osób, pokoi, usług dodatkowych (parking, ognisko ..), czas pobytu od -do

**Recepcjonistka**/**System**
czy pokoje, usługi są dostępne
podać cenę (z uwzględnieniem rabatu, cenników)
podać wysokość zaliczki i termin zapłacenia
Powinna istnieć możliwość pominięcia zaliczki.
Powinna istnieć także możliwość określenia rezerwacji jako potwiedzoną.

**System**
ma wykonać rezerwację wstępną lub potwierdzoną.

## Scen02 rezerwacja, tylko zapytanie ##

Tak samo jak Scen01, ale bez żadnej rezerwacji, tylko odpowiedź, albo wysłanie oferty mejlem, faksem, wydrukowanie

## Scen03, modyfikacja rezerwacji ##

Jest już założona rezerwacja

**Klient** (indywidualny, firma) dzwoni, faksuje, mejluje i chce zmodyfikować rezerwację: może zmienić każdy element rezerwacji: liczbę pokoi, osób, czas itp. itd.

**Recepcjonistka**
Sprawdza dostępność
Modyfikuje rezerwację.
Wylicza nową cenę i wysokość zaliczki


## Scen04, anulacja rezerwacji ##

Jest już założona rezerwacja

**Klient** (indywidualny, firma) dzwoni, faksuje, mejluje i chce anulować rezerwację.

**Recepcjonistka** Anuluje rezerwację
Jeśli wpłacona zaliczka, to pełna swoboda w sprawie zwrotu zaliczki, może być zwrócona w całości, przepaść lib zrócona częściowo.

## Scen05, potwierdzenie rezerwacji ##

Jest już założona rezerwacja

**(Z zewnątrz)** wpływa zaliczka, **klient** (indywidualny, firma) dzwoni, faksuje, mejluje i potwierdza rezerwację

**Recepcjonistka**

Zamienia status rezerwacji na „potwierdzoną”
Drukuje, faksuje, mejluje potwierdzenie rezerwacji.

Powinna istnieć także możliwość potwierdzenia rezerwacji z pominięciem "zapłacenia zaliczki", może także minąć czaz bezkosztowej rezerwacji.

## Scen06, brak potwierdzenia rezerwacji. ##

Jest już założona rezerwacja warunkowa

Mija termin wpłacenia zaliczki, potwierdzenia, czas bezkosztowej rezerwacji.

**System** powinien na ekranie wyraźnie wyświetlić informację:

Rezerwacja, osoba (firma, osoba indywidualna) mija dzisiaj (na żółto), minęła wczoraj (na czerwono) data potwierdzenia, wpłacenia zaliczki

**Recepcjonistka**: powinna wykonać jakieś działanie, zadzwonić, faksowac, wysłać mejla, wyjaśnić sytuację

## Scen07, anulacja niepotwierdzonej rezerwacji ##

Zaistnienie Scen06

Tak jak Scen04, ale z informacją jaka przyczyna

## Scen08, nowy termin potwierdzenia dla niepotwierdzonej rezerwacji ##

Zaistnienie Scen06

Nowy termin wpłacenia zaliczki, potwierdzenia, jak Scen03

# Scenariusze: check-in, check-out #

## Scen20, zameldowanie, check-in gościa, grupy, rezerwacja ##

Przybywa **gość**, grupa.

**Recepcjonistka** melduje gości, przydziela osoby do pokoi
Otwiera rachunek płatnika
Możliwość zmiany w stosunku do rezerwacji: liczby osób, pokoi, terminu itp. itd.

## Scen21, gość grupa, bez rezerwacji ##

Przybywa **gość**, grupa, jak Scen01

**Recepcjonistka** Jeśli możliwe, to zameldowanie jak Scen20

## Scen22, gość, grupa zamawia dodatkowe usługi, zakupy z dopisaniem do rachunku ##

**Gość**, grupa zamawia dodatkowe usługi, płatność przy wymeldowaniu

**Recepcjonistka** Dopisane do rachunku płatnika

## Scen23, gość, członek grupy, zamawia dodatkowe usługi z dopisaniem do własnego rachunku ##

**Gość**, członek grupy, zamiast dodatkowe usługi, ale na własny rachunek

**Recepcjonistka** Dopisanie do rachunku gościa (nie płatnika)

## Scen24, gość, grupa zamawia dodatkowe usługi, płatność gotówką ##

**Gość**, grupa zamawia, kupuje dodatkowe usługi, płaci od razu

**Recepcjonistka** Przyjęcie gotówki, wpłata do kasy, wydanie KP

## Scen25, wymeldowanie gościa, grupy ##

**Gość**, grupa wymeldowywuje się, koniec pobytu

**Recepcjonistka** : widzi rachunek całej grupy oraz zaliczkę
Wystawia rachunek, paragon, fakturę

Możliwość różnej formy płatności, gotówka, przelew itp  Rozbicie płatności na kilka faktur (inni płatnicy). Scalenie kilku rachunków w jeden.
Wystawia rachunki dla członków grupy (Scen23)
Jeśli płatność gotówką, to wpłata do kasy, paragon fiskalny,
Zwolnienie pokoi, pokój do sprzątania

## Scen26, wymeldowanie pojedynczego członka grupy ##

**Recepcjonistka** widzi rachunek własny (Scen23)
Wystawia dokument, fakturę, paragon.
Zwalnia pokój, pokój do sprzątania

## Scen27, zmiana pokoi podczas pobytu, wydłużenie pobytu ##

**Gość**, grupa chce zmienić pokój, wydłużyć pobyt.

**Recepcjonistka**: Sprawdzenie, czy jest to możliwe, modyfikuje rezerwację, przydziela pokój, określa nową cenę
Poprzedni do sprzątania.


## Scen28, pokój posprzątany ##

**Pokojówka** informuje recepcję o posprzątaniu pokoju

**Recepcjonistka** : Zmiana statusu pokoju na „wolny”