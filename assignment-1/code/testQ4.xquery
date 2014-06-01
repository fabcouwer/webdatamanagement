for $p in //person[//first][//last]
where $p/email=’m@home’
return ($p//first, $p//last)