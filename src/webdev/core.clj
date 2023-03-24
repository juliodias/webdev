(ns webdev.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defn say
  [greet]
  {:status 200
   :body greet
   :headers {}})

(defn greet
  [request]
  (cond
    (= "/" (:uri request))
    (say "Hello, World!")
    (= "/goodbye" (:uri request))
    (say "See you later!")
    :else
    {:status 404
     :body "Page Not Found."
     :headers {}}))

(defn about-me
    []
 "Hi, I'm Júlio! I'm a Software Engineer based in Sao Paulo, Brazil.
 This site is just a POC to learn clojure.")

(defroutes app
           (GET "/" [] (say "Hello, World!"))
           (GET "/goodbye" [] (say "Goodbye, cruel world!"))
           (GET "/about" [] (about-me))
           (route/not-found "Page not found!"))

(defn -main
  [port]
  (jetty/run-jetty greet
                   {:port (read-string port)}))

(defn -dev-main
  [port]
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (read-string port)}))
