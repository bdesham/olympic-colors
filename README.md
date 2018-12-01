# olympic-colors

The Olympic flag consists of red, blue, green, yellow, and black rings on a white field. When the flag was introduced in 1914, every nation’s flag used at least one of these six colors. But is this still the case? And does there exist a smaller set of colors with the same property?

I examined this problem in depth in [an article on my website][article]. The function of this small Clojure project is to screen-scrape Wikipedia’s [list of countries by colors of national flags][flags], producing a mapping from countries to flag colors that can be processed by [hitting-set][hitting-set], another library I wrote in connection with this problem.

⚠️ **Notice:** This project is no longer maintained. It may have severe security or compatibility problems. Use it at your own risk!

[article]: https://esham.io/2012/09/olympic-colors
[flags]: http://en.wikipedia.org/wiki/List_of_countries_by_colors_of_national_flags
[hitting-set]: https://github.com/bdesham/hitting-set

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
(minimal-hitting-sets flag-map)
```

By default, the flag color data is taken from the file `flags.html`, which is a copy of the Wikipedia article’s HTML from 2012-08-31. To use “live” data, do

```clj
(color-map (colors-and-countries))
```

This will give output in the same format as returned by `flags`.

## Author

This project was created by [Benjamin Esham](https://esham.io).

This project is [hosted on GitHub](https://github.com/bdesham/olympic-colors). It is **no longer being developed** and is left on GitHub only in the hope that someone will find the code interesting or useful.

## License

Copyright © 2012 Benjamin D. Esham. The code in this project is distributed under the Eclipse Public License, the same as that used by Clojure. A copy of the license is included as “epl-v10.html” in this distribution.

The file “flags.html” is a copy of the HTML source of the Wikipedia article “list of countries by colors of national flags”, which is released under the [Creative Commons Attribution-ShareAlike 3.0 Unported license][cc by-sa]. The list of contributors to that article is [available on Wikipedia][history].

[cc by-sa]: https://en.wikipedia.org/wiki/Wikipedia:Text_of_Creative_Commons_Attribution-ShareAlike_3.0_Unported_License
[history]: https://en.wikipedia.org/w/index.php?title=List_of_flags_by_color_combination&action=history
