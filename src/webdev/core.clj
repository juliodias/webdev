(ns webdev.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.handler.dump :refer [handle-dump]]))

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

(defn yo
  [req]
  (let [name (get-in req [:route-params :name])]
    {:status  200
     :body    (str "Yo, " name "!")
     :headers {}}))

(def operators
  {"+" +
   "-" -
   "/" /
   "*" *})

(defn calculate
  [req]
  (let [route-params  (:route-params req)
        first-number  (Integer. (:first-number route-params))
        second-number (Integer. (:second-number route-params))
        operator      (get operators (:operator route-params))]
    (if operator
      {:status  200
       :body    (str "The result from the operation: " operator " between: " first-number " and " second-number " is: " (operator first-number second-number))
       :headers {}}
      {:status 404
       :body    (str "Unknown operator: " (:operator route-params))
       :headers {}})))

(defroutes app
           (GET "/" [] (say "Hello, World!"))
           (GET "/goodbye" [] (say "Goodbye, cruel world!"))
           (GET "/about" [] (about-me))
           (GET "/request" [] handle-dump)
           (GET "/yo/:name" [] yo)
           (GET "/calc/:first-number/:operator/:second-number" [] calculate)
           (route/not-found "Page not found!"))

(defn -main
  [port]
  (jetty/run-jetty greet
                   {:port (read-string port)}))

(defn -dev-main
  [port]
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (read-string port)}))
