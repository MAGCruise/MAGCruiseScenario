(define *demands* (make ArrayList '(220 280 330 404 476 555 612 636 680 716)))
(define *bonus-demand-ratio* 100)
;;(define *factory-orders* (make ArrayList '(50 100 50 100 50 100 50 100 50 100)))
(define *factory-orders* (make ArrayList '(200 200 200 200 200 200 200 200 200 200)))

(define *shop1-orders*  (make ArrayList '(200 200 200 200 200 200 200 200 200 200)))
(define *shop2-orders*  (make ArrayList '(200 200 200 200 200 200 200 200 200 200)))

;;(define *shop2-orders*  (make ArrayList '(150 150 150 150 150 150 150 150 150 150)))

;;(define *shop1-orders*  (make ArrayList '(100 100 100 100 100 100 100 100 100 100)))
;;(define *shop2-orders*  (make ArrayList '(100 100 100 100 100 100 100 100 100 100)))

;;(define *shop1-prices*  (make ArrayList '(100 100 100 100 100 100 100 100 100 100)))
(define *shop1-prices*  (make ArrayList '(150 150 150 150 150 150 150 150 150 150)))
(define *shop2-prices*  (make ArrayList '(150 150 150 150 150 150 150 150 150 150)))

(define *shop-names* '(Shop1 Shop2))
(define *farmer-names* '(PotatoFarmer))
(define *potato-to-croquette-ratio* 2)
(define *machining-cost-ratio* 20)

(define *farmer-init-deliver* 100)
(define *factory-init-deliver* 100)
(define *shop-init-stock* 100)
(define *factory-init-stock* 400)


(define *stock-cost-ratio* 5)
(define *croquette-cost-ratio* 60)
(define *potato-cost-ratio* 20)

(define (def:game-scenario)
  (def:player 'PotatoFarmer 'agent)
  (def:player 'Factory 'human)
  (def:player 'Shop1 'human)
  (def:player 'Shop2 'human)

  (def:round
    (def:stage 'init
      (def:task 'Factory 'factory:init)
      (def:players-task *shop-names* 'shop:init))
    (def:stage 'farmer-delivery
      (def:players-task *farmer-names* 'farmer:delivery)
      (def:task 'Factory 'factory:receive-delivery))
    (def:stage 'factory-delivery
      (def:task 'Factory 'factory:delivery)
      (def:players-task *shop-names* 'shop:receive-delivery))
    (def:parallel-stage 'shop-order-and-pricing
      (def:players-task *shop-names* 'shop:order-and-pricing))
    (def:stage 'factory-receive-order 
      (def:task 'Factory 'factory:receive-order))
    (def:stage 'factory-order 
      (def:task 'Factory 'factory:order)
      (def:players-task *farmer-names* 'farmer:receive-order))
    (def:parallel-stage 'closing
      (def:task 'Factory 'factory:closing)
      (def:players-task *shop-names* 'shop:closing)))

  (def:rounds 7
    (def:parallel-stage 'status
      (def:players-task *shop-names* 'shop:status)
      (def:task 'Factory 'factory:status))
    (def:restage 'farmer-delivery)
    (def:restage 'factory-delivery)
    (def:restage 'shop-order-and-pricing)
    (def:restage 'factory-receive-order)
    (def:restage 'factory-order)
    (def:restage 'closing))

  (def:round 
    (def:restage 'status)
    (def:restage 'farmer-delivery)
    (def:restage 'factory-delivery)
    (def:parallel-stage 'shop-no-order
      (def:players-task *shop-names* 'shop:no-order))
    (def:parallel-stage 'shop-pricing
      (def:players-task *shop-names* 'shop:pricing))
    (def:restage 'factory-receive-order)
    (def:restage 'factory-order)
    (def:restage 'closing))

  (def:round 
    (def:restage 'status)
    (def:restage 'farmer-delivery)
    (def:restage 'factory-delivery)
    (def:restage 'shop-no-order)
    (def:restage 'shop-pricing)
    (def:restage 'factory-receive-order)
    (def:stage 'factory-no-order
      (def:task 'Factory 'factory:no-order))
    (def:restage 'closing))

  (def:round 
    (def:restage 'status)))

(define (shop:no-order ctx ::Context self ::Player)
  (self:set 'order-of-potato-croquette 0))

(define (factory:no-order ctx ::Context self ::Player)
  (self:set 'order-of-potato 0))


(define (shop:status ctx ::Context self ::Player)
  (ui:show-message self:name 
    (self:history:tabulate
      (cons "発注個数" 'order-of-potato-croquette)
      (cons "納品個数" 'deliverly-of-potato-croquette)
      (cons "顧客数" 'demand-of-potato-croquette)
      (cons "販売個数" 'sales-of-potato-croquette)
      (cons "販売価格" 'price-of-potato-croquette)
      (cons "在庫個数" 'stock-of-potato-croquette)
      (cons "在庫費(円)" 'inventory-cost-of-potato-croquette)
      (cons "仕入費(円)" 'material-cost-of-potato-croquette)
      (cons "売上高(円)" 'earnings-of-potato-croquette)
      (cons "利益(円)" 'profit)))
)

(define (factory:status ctx ::Context self ::Player)
  (ui:show-message self:name 
    (self:history:tabulate
      (cons "発注個数(じゃがいも)" 'order-of-potato)
      (cons "納品個数(じゃがいも)" 'deliverly-of-potato)
      (cons "受注個数" 'order-of-potato-croquette)
      (cons "需要個数" 'demand-of-potato-croquette)
      (cons "販売個数" 'sales-of-potato-croquette)
      (cons "生産個数" 'production-of-potato-croquette)
      (cons "在庫" 'stock-of-potato-croquette)
      (cons "在庫費(円)" 'inventory-cost-of-potato-croquette)
      (cons "仕入費(円)" 'material-cost-of-potato-croquette)
      (cons "売上高(円)" 'earnings-of-potato-croquette)
      (cons "利益(円)" 'profit)))
)


(define (shop:init ctx ::Context self ::Player)
  (define stock *shop-init-stock*)
  (self:set 'stock-of-potato-croquette stock)
  (ui:show-message self:name 
    "<div class=\"alert-danger\">" "ゲームのはじまりです！"
    "<ul>"
      "<li>発注：" "初日(0日目)と翌日(1日目)にそれぞれ100個の冷凍コロッケが届くように発注済みです．"
                   "<br>初日(0日目)に発注した冷凍コロッケは翌々日(2日目)に納品され，その日から販売できます．" "</li>"
      "<li>在庫：" "現在の冷凍コロッケの在庫は" stock "個です．</li>"
    "</ul></div>")
)

(define (factory:init ctx ::Context self ::Player)
  (define stock *factory-init-stock*)
  (self:set 'stock-of-potato-croquette stock)
  (ui:show-message self:name 
    "<div class=\"alert-danger\">" "ゲームのはじまりです！"
    "<ul>"
      "<li>受注：" "ショップ1，ショップ2から，それぞれ" *factory-init-deliver* "個ずつの冷凍コロッケを初日(0日目)と翌日(1日目)に納品するように注文を受けています．"
                   "<br>初日(0日目)に受けた注文は翌々日(2日目)に納品することになります．"
                   "<br>ラウンド終了毎に冷凍コロッケが作成されるので，作成した冷凍コロッケを納品できるのは次ラウンド以降です．" "</li>"
      "<li>在庫：" "現在の冷凍コロッケの在庫は" stock "個です．</li>"
    "</ul></div>")
)

(define (factory:closing ctx ::Context self ::Player)
  (define price ::number *croquette-cost-ratio*)
  (define demand ::number (self:get 'demand-of-potato-croquette))
  (define sales ::number (self:get 'sales-of-potato-croquette))
  (define order ::number (self:get 'order-of-potato))

  (define delivered-potato (self:get 'deliverly-of-potato))
  (define created-croquette (* delivered-potato *potato-to-croquette-ratio*))
  (define machining-cost (* created-croquette *machining-cost-ratio*))
  (define stock-before-production ::number (self:get 'stock-of-potato-croquette))
  (define ordered-croquette ::number (sum (map (lambda (shop ::symbol) (self:get (cons shop 'order-of-potato-croquette))) *shop-names*)))
  
  (define stock ::number (+ stock-before-production created-croquette))

  (define inventory-cost ::number (* stock *stock-cost-ratio*))
  (define earnings ::number (* sales price))
  (define material-cost ::number (* delivered-potato *potato-cost-ratio*))

  (define profit ::number (- earnings (sum (list inventory-cost material-cost machining-cost))))

  (define order-of-potato-croquette (sum (map (lambda (shop-name ::symbol) (self:get (cons shop-name 'order-of-potato-croquette))) *shop-names*)))

  (ui:show-message self:name 
    "<div class=\"alert-danger\">" self:name "の" ctx:roundnum "日目の決算です．"
    "<ul>"
      "<li>発注：" order "個のじゃがいもを発注しました．</li>"
      "<li>納品：" "じゃがいも" delivered-potato "個が納品されました．支払額は" material-cost "です</li>"
      "<li>受注：" ordered-croquette "個の冷凍コロッケの注文を受けました．</li>"
      "<li>販売：" demand "個の冷凍コロッケの納品が必要でした．" "冷凍コロッケを1個" price "円で" sales "個納品しました．" "売り上げは" earnings "円です．</li>"
      "<li>生産：" created-croquette "個の冷凍コロッケを作成しました．" machining-cost "円の加工費がかかりました．</li>"
      "<li>在庫：" "冷凍コロッケの在庫は" stock "個になりました．" "在庫維持費は" inventory-cost "円です．</li>"
    "</ul></div>")
  (self:setAll
    (cons 'order-of-potato-croquette order-of-potato-croquette)
    (cons 'demand-of-potato-croquette demand)
    (cons 'sales-of-potato-croquette sales)
    (cons 'production-of-potato-croquette created-croquette)
    (cons 'stock-of-potato-croquette stock)
    (cons 'inventory-cost-of-potato-croquette inventory-cost)
    (cons 'material-cost-of-potato-croquette material-cost)
    (cons 'earnings-of-potato-croquette earnings)
    (cons 'profit profit)
  )
)

(define (shop:closing ctx ::Context self ::Player)
  (define (get-other) ::Player
    (if (eqv? self:name 'Shop1) (ctx:getPlayer 'Shop2) (ctx:getPlayer 'Shop1)))

  (define total-price ::number
    (let ((shops (apply ctx:getPlayers *shop-names*)))
          (sum (map (lambda (shop ::Player) (shop:get 'price-of-potato-croquette)) shops))))
  (define price ::number (self:get 'price-of-potato-croquette))
  (define demand ::number (floor->exact (* (*demands* ctx:roundnum) (/ (- total-price price) total-price))))
  
  (define other-price ::number ((get-other):get 'price-of-potato-croquette))
  (when (< price other-price) (set! demand (+ demand *bonus-demand-ratio*)))

  (define stock-before-sales ::number (self:get 'stock-of-potato-croquette))
  (define sales ::number (if (<= stock-before-sales demand) stock-before-sales demand)) 
  (define delivered ::number (self:get 'deliverly-of-potato-croquette))
  (define order ::number (self:get 'order-of-potato-croquette))
  
  (define stock ::number (- stock-before-sales sales))

  (define inventory-cost ::number (* stock *stock-cost-ratio*))
  (define earnings ::number (* sales price))
  (define material-cost ::number (* delivered *croquette-cost-ratio*))

  (define profit ::number (- earnings (sum (list inventory-cost material-cost))))

  (ui:show-message self:name
    "<div class=\"alert-danger\">" self:name "の" ctx:roundnum "日目の決算です．"
    "<ul>"
      "<li>発注：" order "個の冷凍コロッケを発注しました．この冷凍コロッケは明後日に納品予定です．</li>"
      "<li>納品：" delivered "個の冷凍コロッケが納品されました．" "支払額は" material-cost "円です．</li>"
      "<li>販売：" "お店にはお客さんが" demand "個のコロッケを買いにきました．"
                    "コロッケを1個" price "円で販売し，" sales "個が売れました．" "売り上げは" earnings "円です．" "</li>"
      "<li>在庫：" "在庫は冷凍コロッケ" stock "個になりました．" "在庫維持費は" (* stock *stock-cost-ratio*) "円です．</li>"
      "<li>競合店：" "競合店は" other-price "円でコロッケを販売していました．" "</li>"
    "</ul></div>")
  (self:setAll
    (cons 'demand-of-potato-croquette demand)
    (cons 'sales-of-potato-croquette sales)
    (cons 'stock-of-potato-croquette stock)
    (cons 'inventory-cost-of-potato-croquette inventory-cost)
    (cons 'material-cost-of-potato-croquette material-cost)
    (cons 'earnings-of-potato-croquette earnings)
    (cons 'profit profit)
 )
)


(define (shop:order ctx ::Context self ::Player)
  (define shop-order ((if (eqv? self:name 'Shop1) *shop1-orders* *shop2-orders*) ctx:roundnum))
  (ui:request-to-input self:name
    (ui:form
      (to-string ctx:roundnum "日目です．" self:name "さん，コロッケ工場へ発注して下さい．発注したものは，明後日に納品されます．")
      (ui:val-input "個数(冷凍コロッケ)" 'num-of-potato-croquette shop-order))
    (lambda (num-of-potato-croquette)
      (self:set 'order-of-potato-croquette num-of-potato-croquette)
      (define msg ::Message (self:makeMessage 'order (cons 'num-of-potato-croquette num-of-potato-croquette)))
      (manager:send-message 'Factory msg))))

(define (shop:pricing ctx ::Context self ::Player)
  (define shop-price ((if (eqv? self:name 'Shop1) *shop1-prices* *shop2-prices*) ctx:roundnum))
  (ui:request-to-input self:name
    (ui:form
          (to-string ctx:roundnum "日目です．" self:name "さん，今日のコロッケの販売価格を決定して下さい．")
      (ui:val-input "販売価格(コロッケ)" 'price-of-potato-croquette shop-price))
    (lambda (price-of-potato-croquette)
      (self:set 'price-of-potato-croquette price-of-potato-croquette))))

(define (shop:order-and-pricing ctx ::Context self ::Player)
  (shop:order ctx self)
  (shop:pricing ctx self))


(define (shop:receive-delivery ctx ::Context self ::Player)
  (define delivered ((self:msgbox:pop):get 'num-of-delivery))
  (define stock ::number (self:get 'stock-of-potato-croquette))
  (self:setAll
    (cons 'deliverly-of-potato-croquette delivered)
    (cons 'stock-of-potato-croquette (+ stock delivered)))
  (ui:show-message self:name 
     ctx:roundnum "日目です．" "冷凍コロッケが" delivered "個納品されました．"))


(define (factory:receive-order ctx ::Context self ::Player)
  (for-each
    (lambda (msg ::Message)
      (self:set (cons msg:from 'order-of-potato-croquette) (msg:get 'num-of-potato-croquette))
      (ui:show-message self:name ctx:roundnum "日目です．" msg:from "から冷凍コロッケ" (msg:get 'num-of-potato-croquette) "個の注文を受けました．"))
    (self:msgbox:popAll)))


(define (factory:order ctx ::Context self ::Player)
  (ui:request-to-input self:name
    (ui:form
      (to-string
        ctx:roundnum "日目です．" self:name "さん，農場へじゃがいもを発注して下さい．発注したものは，明日に納品されます．")
      (ui:val-input "個数(ジャガイモ)" 'potato (*factory-orders* ctx:roundnum)))
    (lambda (potato)
      (self:set 'order-of-potato potato)
      (manager:send-message 'PotatoFarmer (self:makeMessage 'order (cons 'order-of-potato potato))))))

(define (factory:delivery ctx ::Context self ::Player)
  (define factory:sales 0)
  (define factory:demand 0)
  (define stock-before-delivery (self:get 'stock-of-potato-croquette))
  (define total-order (if (< ctx:roundnum 2) (* *factory-init-deliver* 2) (sum (map (lambda (s) (self:prev 2 (cons s 'order-of-potato-croquette))) *shop-names*))))

  (for-each
    (lambda (shop-name ::symbol)
      (define order  (if (< ctx:roundnum 2) *factory-init-deliver* (self:prev 2 (cons shop-name 'order-of-potato-croquette))))
      (define delivery (if (<= total-order stock-before-delivery) order (floor->exact (* stock-before-delivery (/ order total-order)))))
      (define stock (self:get 'stock-of-potato-croquette))
      (self:set 'stock-of-potato-croquette (- stock delivery))
      (set! factory:demand (+ factory:demand order))
      (set! factory:sales (+ factory:sales delivery))
      (ui:show-message self:name
        ctx:roundnum "日目です．" shop-name "に冷凍コロッケ" delivery "個を納品しました．")
      (manager:send-message shop-name (self:makeMessage 'delivery (cons 'num-of-delivery delivery))))
    *shop-names*)
    (self:setAll (cons 'demand-of-potato-croquette factory:demand)(cons 'sales-of-potato-croquette factory:sales)))


(define (factory:receive-delivery ctx ::Context self ::Player)
  (define msg ::Message (self:msgbox:pop))
  (define delivered-potato (msg:get 'num-of-delivery))
  (self:set 'deliverly-of-potato delivered-potato)
  (ui:show-message self:name ctx:roundnum "日目です．" "じゃがいも" delivered-potato "個が納品されました．"))

(define (farmer:receive-order ctx ::Context self ::Player)
  (define msg ::Message (self:msgbox:pop))
  (define order (msg:get 'order-of-potato))
  (self:set 'received-order-of-potato order)
  (ui:show-message self:name  ctx:roundnum "日目です．" "じゃがいも" order "個の注文を受けました．"))

(define (farmer:delivery ctx ::Context self ::Player)
  (define (farmer:delivery-aux order)
    (self:set 'delivered-num-of-potato order)
    (manager:send-message 'Factory (self:makeMessage 'delivery (cons 'num-of-delivery order))))
  (cond ((< ctx:roundnum 1) (farmer:delivery-aux *farmer-init-deliver*))
        ((farmer:delivery-aux (self:prev 1 'received-order-of-potato)))))
