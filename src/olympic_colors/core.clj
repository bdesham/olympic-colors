(ns olympic-colors.core
  (:use [clojure.set :only [difference]]
        net.cgrand.enlive-html)
  (:require [clojure.string :as string]))

(defn- split-colors
  "Takes a string of the form 'blue', 'blue and white', 'blue, white, black,
  and red', etc. and converts it into a set of lowercase color-name keywords."
  [s]
  (set (map #(-> % string/lower-case keyword)
            (string/split s #",? (and )?"))))

(defn- remove-color
  "Given a map from keys to sets of colors, deletes the color c from every set.
  Deletes any keys which would then map to #{}."
  ([m c]
   (remove-color m c {}))
  ([m c res]
   (if-let [pair (first m)]
     (recur (rest m)
            c
            (let [new-value (difference (second pair) #{c})]
              (if (> (count new-value) 0)
                (assoc res
                       (first pair)
                       new-value)
                res)))
     res)))

(defn colors-and-countries
  "Uses enlive to take Wikipedia's list of national flags and extract the HTML
  elements of interest: the <span>s within each heading (these indicate colors)
  and the <a>s in list items (these give the country names). This function
  tells enlive which elements to select but does no further processing; in
  particular, the 'see also' links at the bottom of the article will be
  included in the output from this function.
  
  When called with no arguments, fetch the data from Wikipedia; if a filename
  is passed, it is assumed to be a local copy of the Wikipedia article's HTML."
  ([]
   (colors-and-countries (java.net.URL.
               "http://en.wikipedia.org/wiki/List_of_countries_by_colors_of_national_flags")))
  ([filename]
  (let [flags (html-resource filename)]
    (select flags #{[:div#mw-content-text #{:h2 :h3} :> :span.mw-headline]
                    [:div#mw-content-text :ul :> :li [:a
                                                      first-of-type
                                                      (attr? :title)
                                                      (attr-starts :href "/wiki/")]]}))))

(defn color-map
  "Given the output of colors-and-countries, returns a map where the keys are
  country names (as strings) and the values are the colors of those countries'
  flags (sets of keywords)."
  ([t]
   (color-map t {} #{:MISSING}))
  ([t m colors]
   (if-let [el (first t)]
     (if (= (get-in el [:attrs :id]) "See_also")
       m
       (if (= (el :tag) :span)
         (recur (rest t)
                m
                (split-colors (first (el :content))))
         (recur (rest t)
                (assoc m (first (el :content)) colors)
                colors)))
     m)))

(defn flags
  []
  (color-map (colors-and-countries)))
