# olympic-colors

The Olympic flag consists of red, blue, green, yellow, and black rings on a white field. When the flag was introduced in 1914, every nation’s flag used at least one of these six colors. But is this still the case? And does there exist a smaller set of colors with the same property?

I examined this problem in depth in [an article on my website][article]. The function of this small Clojure project is to screen-scrape Wikipedia’s [list of countries by colors of national flags][flags], producing a mapping from countries to flag colors that can be processed by [hitting-set][hitting-set], another library I wrote in connection with this problem.

[article]: http://www.bdesham.info/2012/09/olympic-colors
[flags]: http://en.wikipedia.org/wiki/List_of_countries_by_colors_of_national_flags
[hitting-set]: https://github.com/bdesham/hitting-set

Version numbers are assigned to this project according to version 1.0.0 of the [Semantic Versioning](http://semver.org/) specification.

## Usage

This is not a library as such; to tinker with it, just run `lein repl` and play around:

```
git clone git://github.com/bdesham/olympic-colors.git
cd olympic-colors
lein repl
```

The main function of interest is `flags`, which takes no arguments, and which produces a map of countries’ flags’ colors in the following form:

```clj
{"Australia" #{:white :red :blue},
 "Tanzania" #{:black :blue :green :yellow},
 "Norway" #{:white :red :blue},
 "Uruguay" #{:white :blue :yellow},
 "Saint Vincent and the Grenadines" #{:blue :green :yellow},
 "Ivory Coast" #{:white :orange :green},
 "Sierra Leone" #{:white :blue :green},
 "United States" #{:white :red :blue}}
```

This format is suitable for feeding to the `hitting-set` library, specifically by doing something like

```clj
(def flag-map (flags))
(smallest-hitting-sets flag-map)
```

## License

Copyright © 2012 Benjamin D. Esham (www.bdesham.info).

The code in this project is distributed under the Eclipse Public License, the same as that used by Clojure. A copy of the license is included as “epl-v10.html” in this distribution.

The file “flags.html” is a copy of the HTML source of the Wikipedia article “list of countries by colors of national flags”, which is released under the [Creative Commons Attribution-ShareAlike 3.0 Unported license][cc by-sa].

[cc by-sa]: https://en.wikipedia.org/wiki/Wikipedia:Text_of_Creative_Commons_Attribution-ShareAlike_3.0_Unported_License
