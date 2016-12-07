(ns adventofcode.day7-test
  (:require [adventofcode.day7 :refer :all]
            [clojure.test :refer :all]))

(deftest tls-test
  (is (= true (tls? (parse-ip "abba[mnop]qrst"))))
  (is (= false (tls? (parse-ip "abcd[bddb]xyyx"))))
  (is (= false (tls? (parse-ip "aaaa[qwer]tyui"))))
  (is (= true (tls? (parse-ip "ioxxoj[asdfgh]zxcvbn")))))
