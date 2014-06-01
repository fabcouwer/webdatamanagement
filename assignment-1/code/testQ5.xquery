for $p in //person
where $p/email=’m@home’
return <res>{$p/*/last}</res>