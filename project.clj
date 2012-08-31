(defproject olympic-colors "1.0.0"
            :description "Find a minimal hitting set of colors in national flags"
            :url "https://github.com/bdesham/olympic-colors"
            :license {:name "Eclipse Public License"
                      :distribution :repo
                      :url "http://www.eclipse.org/legal/epl-v10.html"
                      :comment "Same as Clojure"}
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [enlive "1.0.1"]
                           [hitting-set "0.8.0-SNAPSHOT"]]
            :main olympic-colors)
