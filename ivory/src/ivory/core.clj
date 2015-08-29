(ns ivory.core
  (:require [compojure.route :as route :refer [files not-found]]
            [compojure.handler :refer [site]] ; form, query params decode; cookie; session, etc
            [compojure.core :refer [defroutes GET POST DELETE ANY context]]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :as hiccup :refer [html]]))

(defn show-landing-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "<h1>Landing Page</h2>"})

(defn hello-name [name]
  (hiccup/html
    [:html
     [:body
      [:h1 {:class "title"}
       (str "Hello " name)]]]))

(defn status [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Server is working fine"})

(defroutes all-routes
  (GET "/" [] show-landing-page)
  (GET "/hello/:name" [name] hello-name)
  (route/resources "/")
  (route/not-found "<p>404, Page not found.</p>")) ;; all other, return 404

(defn -main []
  (run-server (site #'all-routes) {:port 8080}))