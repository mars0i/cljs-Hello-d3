;; Clojurescripot Hello World with basic d3.js examples

(ns hello-world.hello
  (:require [clojure.string :as str]))

(def width  960)
(def height 500)

;;;;;;;;;;;;;;;;;;;;;;
;; Some html: the "Hello World!"
(.write js/document "<center><h1>Whatever, daddyo!</h1></center>")

;;;;;;;;;;;;;;;;;;;;;;
;; Three overlapping circles, based on example in the strokes repo:
;; https://github.com/dribnet/strokes/tree/master/examples/venn-simple

;; Note clj->js turns a Clojure map into a Javascript Object.
;; Passing an Object to d3's attr is allowed as a way of setting
;; multiple attributes in one call to attr.

;; Create an svg
(def svg1 (-> js/d3
              (.select "body")
              (.append "svg")
              (.attr (clj->js {:width width :height height}))))

(-> svg1 (.append "circle")
    (.attr (clj->js {:cx 680 :cy 280 :r 150 :class "right"})))

(-> svg1 (.append "circle")
    (.attr (clj->js {:cx 550 :cy 200 :r 175 :class "center"})))

(-> svg1 (.append "circle")
    (.attr (clj->js {:cx 450 :cy 300 :r 200 :class "left"})))

;;;;;;;;;;;;;;;;;;
;; Translation with minor modifications of Bostock's general update pattern I 
;; example: http://bl.ocks.org/mbostock/3808218
;; Some of the comments are copied directly from Bostock's code.
;; (With help from consultation of the version in the strokes 
;; repo, 
;; https://github.com/dribnet/strokes/blob/master/examples/general-update-pattern/src/gup1/gup1.cljs
;; which was similar to my original attempt.)

;(declare d3update)
; If d3update defn is not before its use, this allows it compile, but
; it won't run in the browser.  Wierdness of clojure->javascript
; interop.  Not sure why.

(def alphabet (map char (range 97 (+ 97 26)))) ; lowercase letters a through z

;; Create a g attached to a new svg; we'll add the letters to the g.
;; (Simply called "svg" in Bostock's example.)
(def svg2g (-> js/d3 
              (.select "body")
              (.append "svg")
              (.attr (clj->js {:width width :height height}))
              (.append "g") ; so we can move the letters to a nice place in svg
              (.attr "transform" (str "translate(32," (/ height 3) ")")))) 

(defn d3update 
  "Given data in a Clojurescript sequence, converts it to a Javascript array
  and uses it to update the previous d3 display--either by creating something
  where there was nothing, by adding to it, by shortening it, etc."
  [data]
  ;; Here's one way to print to console.  
  ;(.log js/console (pr-str data)) ;(.log js/console (pr-str (type data)))
  ;; Note Clojurescript doesn't Clojure's class function, but you can use type.

  ;; DATA JOIN
  ;; Join new data with old elements, if any.
  (let [text (-> svg2g (.selectAll "text") (.data (clj->js data)))] ; note conversion

    ;; UPDATE
    ;; Update old elements as needed.
    (.attr text "class" "update") ; i.e. since they're old, we reclassify them

    ;; ENTER
    ;; Create new elements as needed.
    (-> text 
        (.enter)
        (.append "text")
        (.attr (clj->js {:class "enter"    ; new elts have a different class than old
                         :x     #(* %2 32) ; move each element 32px right of preceding
                         :dy    ".35em"})))

    ;; ENTER + UPDATE
    ;; Appending to the enter selection expands the update selection to include
    ;; entering elements; so, operations on the update selection after appending to
    ;; the enter selection will apply to both entering and updating nodes.
    (.text text identity) ; display letter that's the data elt

    ;; EXIT
    ;; Remove old elements as needed.
    (-> text (.exit) (.remove)))) ; strip out the ones that didn't match


;; Initial display of entire alphabet
(d3update alphabet)

;; Function that will be called repeatedly after a delay to change the display.
(defn intervalfn
  "Selects a random subset of letters from alphabet, alphabetizes the result,
  and passes it to d3update."
  []
  (-> alphabet
      shuffle
      (subvec (rand-int 26)) ; since we shuffled the seq, we can just take the rest starting from a rand int
      sort       ; put 'em back in alphabetical order
      d3update)) ; will convert it to a Javascript array before doing anything


;; Call the intervalfn function after some number of milliseconds.
;; Note the window parameter wasn't needed in the original Javascript, but
;; we need it here.  Not sure why there's a difference.
(.setInterval js/window intervalfn 1500)
