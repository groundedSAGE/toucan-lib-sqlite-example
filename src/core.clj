(ns core
  (:require [toucan.db :as db]
            [toucan.models :as models :refer [defmodel]]
            [clojure.java.io :as io]
            [honeysql.core :refer [raw]]))

(def db-spec {:dbtype "sqlite"
              :dbname "example"})


(defn get-sql 
  "Gets a sql file of the classpath and reads it in"
  [file]
  (slurp (io/resource file)))

(comment

  ;; REPL-Driven Code
  
  
  ;; Set up Toucan Connection
  (db/set-default-db-connection! db-spec)  

  
  ;; Create a table from sql file
  (db/execute! (raw (get-sql "sql/users.sql")))

  ;; Define a model and it's associated table
  (defmodel Address :address)  
  
  ;; Select from address table with Toucan
  (db/select Address)

  
  (db/insert! Address {:name "New", :email "test@nomail.com"})

  (db/insert-many! Address [{:name "Test1", :email "test"}
                            {:name "Test2", :email "test"}])

  ;; Format Blocker
  )
