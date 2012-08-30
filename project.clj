(defproject olympic-colors "0.9.0-SNAPSHOT"
            :description "Find a minimal hitting set of colors in national flags"
            :url "https://github.com/bdesham/olympic-colors"
            :license {:name "Eclipse Public License"
                      :distribution :repo
                      :url "http://www.eclipse.org/legal/epl-v10.html"
                      :comment "Same as Clojure"}
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [enlive "1.0.1"]
                           [hitting-set "0.8.0-SNAPSHOT"]]
            :plugins [[s3-wagon-private "1.1.2"]]
            :repositories {"private" {:url "s3p://0f48ffb578a4.jars/releases/"
                                      :creds :gpg}}
            :main olympic-colors.core)

(cemerick.pomegranate.aether/register-wagon-factory!
  "s3p" #(eval '(org.springframework.aws.maven.PrivateS3Wagon.)))
