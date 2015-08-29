(ns ivory.core
  (:require [compojure.route :as route :refer [files not-found]]
            [compojure.handler :refer [site]] ; form, query params decode; cookie; session, etc
            ;[compojure.core :refer [defroutes GET POST DELETE ANY context]]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :as hiccup :refer [html]]
            [ring.util.http-response :refer [ok]]
            [compojure.api.sweet :refer :all]
            ;[compojure.api.upload :refer :all]
            [schema.core :as s]
            [clojure.java.io :refer [resource]]
            [ring.middleware.params :refer :all]
            [ring.middleware.multipart-params :refer :all]
            [noir.io :as io]
            [noir.response :as response]
            [ring.util.response :refer [file-response]])
  (:use mikera.image.colours)
  (:use mikera.image.core)
  (:use mikera.image.filters)
  (:use mikera.image.spectrum))

(def resource-path "/tmp/")

(defn home-page [req]
  (hiccup/html [:form {:action "/upload" :method "post" :enctype "multipart/form-data"}
                [:input {:name "file" :type "file" :size "20"}]
                [:input {:type "submit" :name "submit" :value "submit"}]]))

(defn status [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Server is working fine"})

(defn img [filename]
  (load-image (str "/tmp/" filename)))

(defapi all-routes
  (GET* "/" [] home-page)
  (context* "/api" []
            (GET* "/user/:id" [id] (ok {:id id}))
            (POST* "/echo" {body :body-params} (ok body)))
  (POST* "/upload" [file]
         (io/upload-file resource-path file)
         ;(img (str file))
         ;(show ((brightness 2.0) img))
         ;(save img (str "/tmp/" "new-" file) :quality 0.1 :progressive true)
         (response/redirect
           (str "/files/" (:filename file))))
  (GET* "/files/:filename" [filename]
        (file-response (str resource-path filename)))
  (route/resources "/")
  (route/not-found "<p>404, Page not found.</p>")
  )

(defn -main []
  (run-server (site #'all-routes) {:port 8080}))