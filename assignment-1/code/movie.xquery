for $m in //movie
where $m//year='2002'
return ($m//title, $m//genre)