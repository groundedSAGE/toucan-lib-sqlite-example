(ns core
  (:require [toucan.db :as db]
            [toucan.models :as models :refer [defmodel]]
            [next.jdbc :as jdbc]
            [clojure.java.io :as io]))

(def db2 {:dbtype "sqlite"
          :dbname "example"})


(defn get-sql 
  "Gets a sql file of the classpath and reads it in"
  [file]
  (slurp (io/resource file)))

(comment

  ;; REPL-Driven Code
  
  (db/set-default-db-connection! db2)   ; Set up Toucan Connection
  
  (def ds (jdbc/get-datasource db2))    ; Set up JDBC connection
  

   ;; Create a table from a string
  (jdbc/execute! ds ["
create table address (
  id integer primary key autoincrement,
  name varchar(32),
  email varchar(255)
)"])

   ;; Create a table from an sql file
  (jdbc/execute! ds [(get-sql "sql/users.sql")])



   ;; Insert into the address table
  (jdbc/execute! ds ["
insert into address(name,email)
  values('Sean Corfield','sean@corfield.org')"])

  ;; Select from the address table using raw JDBC
  (jdbc/execute! ds ["select * from address"])
  
  
  ;; Define a model and it's associated table
  (defmodel Address :address)  
  
  ;; Select from address table with Toucan
  (db/select Address)

  

  (db/insert! Address {:name "New", :email "test"})

  (db/insert-many! Address [{:name "Test1", :email "test"}
                            {:name "Test2", :email "test"}])

  ;; Format Blocker
  )