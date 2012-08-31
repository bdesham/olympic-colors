(ns olympic-colors
  (:use [clojure.set :only [difference]]
        net.cgrand.enlive-html
        ;[hitting-set :only [enumerate-hitting-sets smallest-hitting-sets]]
        )
  (:require [clojure.string :as string]))

(defn- split-colors
  "Takes a string of the form 'blue', 'blue and white', 'blue, white, black,
  and red', etc. and converts it into a set of lowercase color-name keywords."
  [s]
  (set (map #(-> % string/lower-case keyword)
            (string/split s #",? (and )?"))))

(defn- remove-color
  "Given a map m from keys to sets of colors, deletes the color c from every
  set. Deletes any keys which would be left mapping to #{}."
  [m c]
  (loop [m m,
         res {}]
    (if-let [pair (first m)]
      (recur (rest m)
             (let [new-value (difference (second pair) #{c})]
               (if (> (count new-value) 0)
                 (assoc res
                        (first pair)
                        new-value)
                 res)))
      res)))

(def ^:private wikipedia-url
  "http://en.wikipedia.org/wiki/List_of_countries_by_colors_of_national_flags")

(defn- colors-and-countries
  "Uses enlive to take Wikipedia's list of national flags and extract the HTML
  elements of interest: the <span>s within each heading (these indicate colors)
  and the <a>s in list items (these give the country names). This function
  tells enlive which elements to select but does no further processing; pass
  the output of this function to color-map for a useful result.
  
  When called with no arguments, fetch the data from Wikipedia; if an argument
  is passed, it should be a filename, java.io.File, etc. that points to a copy
  of the Wikipedia article's HTML."
  ([]
   (colors-and-countries (java.net.URL. wikipedia-url)))
  ([filename]
   (let [flags (html-resource filename),
         pass1 (select flags #{[:div#mw-content-text #{:h2 :h3 :h4} :> :span.mw-headline]
                               [:div#mw-content-text :ul :> :li :>
                                [:a first-of-type (attr-starts :href "/wiki/")]]}),
         pass2 (butlast (first (select pass1 {[:*] [[:span (attr= :id "See_also")]]})))]
     pass2)))

(defn- color-map
  "Given the output of colors-and-countries, returns a map where the keys are
  country names (as strings) and the values are the colors of those countries'
  flags (as sets of keywords). If the value :MISSING appears in any of the
  color sets then the input was malformed."
  [tree]
  (loop [t tree,
         m {},
         colors #{:MISSING}]
    (if-let [el (first t)]
      (if (= (el :tag) :span)
        (recur (rest t)
               m
               (split-colors (first (el :content))))
        (recur (rest t)
               (assoc m (first (el :content)) colors)
               colors))
      m)))

(defn flags
  "Returns a map where the keys are country names (as strings) and the values
  are the colors of those countries' flags (as sets of keywords)."
  []
  (color-map (colors-and-countries (java.io.File. "flags.html"))))
