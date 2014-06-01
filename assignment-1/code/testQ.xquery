for $p in //person[//last]
where $p/email='m@home', $p//last='Jones'
return ($p//first, $p//last)