CREATE OR REPLACE PROCEDURE SIMPLEPROC(OUT RES INTEGER,IN SOU INTEGER)
   LANGUAGE SQL
BEGIN
  SET RES = SOU;
END
@

create or replace function RETPROC(IN RET INTEGER)
returns integer
begin
  return ret;
end
@