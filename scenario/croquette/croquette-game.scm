(define-namespace shop "shop")
(define-namespace factory "factory")
(define-namespace farmer "farmer")

(define *shop-names* '(Shop1 Shop2))

(define-alias CroquetteDelivery org.magcruise.gaming.scenario.croquette.msg.CroquetteDelivery)
(define-alias CroquetteOrder org.magcruise.gaming.scenario.croquette.msg.CroquetteOrder)
(define-alias PotatoOrder org.magcruise.gaming.scenario.croquette.msg.PotatoOrder)
(define-alias PotatoDelivery org.magcruise.gaming.scenario.croquette.msg.PotatoDelivery)
(define-alias Shop org.magcruise.gaming.scenario.croquette.player.Shop)
(define-alias Farmer org.magcruise.gaming.scenario.croquette.player.Farmer)
(define-alias Factory org.magcruise.gaming.scenario.croquette.player.Factory)
(define-alias Market org.magcruise.gaming.scenario.croquette.Market)

(define (def:game-scenario)
  (def:ext-context Market)
  
  (def:ext-player 'Farmer 'agent Farmer)
  (def:ext-player 'Factory 'human Factory)
  (def:ext-players *shop-names* 'Shop1 'human Shop)

  (def:round
    (def:stage 'init
      (def:task 'Factory 'factory:init)
      (def:task 'Farmer 'farmer:init)
      (def:players-task *shop-names* 'shop:init))
    (def:stage 'farmer-delivery
      (def:task 'Farmer 'farmer:delivery)
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
      (def:task 'Farmer 'farmer:receive-order))
    (def:parallel-stage 'closing
      (def:task 'Factory 'factory:closing)
      (def:players-task *shop-names* 'shop:closing)))

  (def:rounds 1
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

(define (shop:no-order ctx ::Market self ::Shop)
  (self:set 'order-of-croquette 0))

(define (factory:no-order ctx ::Market self ::Factory)
  (self:set 'order-of-potato 0))


(define (shop:status ctx ::Market self ::Shop)
  (ui:show-message self:name 
    (self:history:tabulate
      (cons "発注個数" 'order) (cons "納品個数" 'delivery)
      (cons "顧客数" 'demand) (cons "販売個数" 'sales) 
      (cons "販売価格" 'price) (cons "在庫個数" 'stock)
      (cons "在庫費(円)" 'inventoryCost) (cons "材料費(円)" 'materialCost)
      (cons "売上高(円)" 'earnings) (cons "利益(円)" 'profit))))

(define (factory:status ctx ::Market self ::Factory)
  (ui:show-message self:name 
    (self:history:tabulate
      (cons "発注個数(じゃがいも)" 'orderOfPotato) (cons "納品個数(じゃがいも)" 'deliveredPotato)
      (cons "受注個数" 'orderedCroquette) (cons "需要個数" 'demand)
      (cons "販売個数" 'sales) (cons "生産個数" 'production)
      (cons "在庫個数" 'stock) (cons "在庫費(円)" 'inventoryCost)
      (cons "材料費(円)" 'materialCost) (cons "加工費(円)" 'machiningCost)
      (cons "売上高(円)" 'earnings)
      (cons "利益(円)" 'profit))))


(define (shop:init ctx ::Market self ::Shop)
  (self:init)
  (ui:show-message self:name 
    (<div-class> "alert alert-danger"
      (to-string "ゲームのはじまりです！"
        (<ul>
          (<li> "発注：" "初日(0日目)と翌日(1日目)にそれぞれ100個の冷凍コロッケが届くように発注済みです．"
                     "<br>初日(0日目)に発注した冷凍コロッケは翌々日(2日目)に納品され，その日から販売できます．")
          (<li> "在庫：" "現在の冷凍コロッケの在庫は" self:stock "個です．"))))))

(define (factory:init ctx ::Market self ::Factory)
  (self:init)
  (ui:show-message self:name 
    (<div-class> "alert alert-danger"
      (to-string "ゲームのはじまりです！"
      (<ul>
        (<li> "受注：" "ショップ1，ショップ2から，それぞれ" self:initDelivary "個ずつの冷凍コロッケを初日(0日目)と翌日(1日目)に納品するように注文を受けています．"
                     "<br>初日(0日目)に受けた注文は翌々日(2日目)に納品することになります．"
                     "<br>ラウンド終了毎に冷凍コロッケが作成されるので，作成した冷凍コロッケを納品できるのは次ラウンド以降です．")
        (<li> "在庫：" "現在の冷凍コロッケの在庫は" self:stock "個です．"))))))


(define (factory:closing ctx ::Market self ::Factory)
  (self:closing)
  (ui:show-message self:name 
    (<div-class> "alert alert-danger"
      (to-string self:name "の" ctx:roundnum "日目の決算です．"
        (<ul>
          (<li> "発注：" self:orderOfPotato "個のじゃがいもを発注しました．")
          (<li> "納品：" "じゃがいも" self:deliveredPotato "個が納品されました．支払額は" self:materialCost "です")
          (<li> "受注：" self:orderedCroquette "個の冷凍コロッケの注文を受けました．")
          (<li> "販売：" self:demand "個の冷凍コロッケの納品が必要でした．" "冷凍コロッケを1個" self:price "円で"
                         self:sales "個納品しました．" "売り上げは" self:earnings "円です．")
          (<li> "生産：" self:production "個の冷凍コロッケを作成しました．" self:machiningCost "円の生産費がかかりました．")
          (<li> "在庫：" "冷凍コロッケの在庫は" self:stock "個になりました．" "在庫維持費は" self:inventoryCost "円です．"))))))

(define (shop:closing ctx ::Market self ::Shop)
  (define demands (make ArrayList '(220 280 330 404 476 555 612 636 680 716)))
  (define (get-other) ::Shop
    (if (eqv? self:name 'Shop1) (ctx:getPlayer 'Shop2) (ctx:getPlayer 'Shop1)))

  (self:closing (get-other) (demands ctx:roundnum))

  (ui:show-message self:name
    (<div-class> "alert alert-danger"
      (to-string self:name "の" ctx:roundnum "日目の決算です．"
      (<ul>
        (<li> "発注：" self:order "個の冷凍コロッケを発注しました．この冷凍コロッケは明後日に納品予定です．")
        (<li> "納品：" self:delivery "個の冷凍コロッケが納品されました．" "支払額は" self:materialCost "円です．")
        (<li> "販売：" "お店にはお客さんが" self:demand "個のコロッケを買いにきました．"
                      "コロッケを1個" self:price "円で販売し，" self:sales "個が売れました．" "売り上げは" self:earnings "円です．" )
        (<li> "在庫：" "在庫は冷凍コロッケ" self:stock "個になりました．" "在庫維持費は" self:inventoryCost "円です．")
        (<li> "競合店：" "競合店は" (get-other):price "円でコロッケを販売していました．"))))))


(define (shop:order ctx ::Market self ::Shop)
  (ui:request-input self:name
    (ui:form
      (to-string ctx:roundnum "日目です．" self:name "さん，コロッケ工場へ発注して下さい．発注したものは，明後日に納品されます．")
      (ui:val-input "個数(冷凍コロッケ)" 'num-of-croquette (self:orders ctx:roundnum)))
    (lambda (num-of-croquette)
      (self:order num-of-croquette)
      (manager:send-message 'Factory (make CroquetteOrder self:name num-of-croquette)))))

(define (shop:pricing ctx ::Market self ::Shop)
  ;;(define shop1-prices  (make ArrayList '(100 100 100 100 100 100 100 100 100 100)))
  (define shop1-prices  (make ArrayList '(150 150 150 150 150 150 150 150 150 150)))
  (define shop2-prices  (make ArrayList '(150 150 150 150 150 150 150 150 150 150)))

  (define shop-price ((if (eqv? self:name 'Shop1) shop1-prices shop2-prices) ctx:roundnum))
  (ui:request-input self:name
    (ui:form
          (to-string ctx:roundnum "日目です．" self:name "さん，今日のコロッケの販売価格を決定して下さい．")
      (ui:val-input "販売価格(コロッケ)" 'price shop-price))
    (lambda (price-of-croquette)
      (self:price price-of-croquette))))

(define (shop:order-and-pricing ctx ::Market self ::Player)
  (shop:order ctx self)
  (shop:pricing ctx self))

(define (shop:receive-delivery ctx ::Market self ::Shop)
  (define msg ::CroquetteDelivery (self:msgbox:pop))
  (self:receiveDelivery msg:num)
  (ui:show-message self:name 
     ctx:roundnum "日目です．" "冷凍コロッケが" msg:num "個納品されました．"))


(define (factory:receive-order ctx ::Market self ::Factory)
  (for-each
    (lambda (msg ::CroquetteOrder)
      (self:receiveOrder msg)
      (ui:show-message self:name ctx:roundnum "日目です．" msg:from "から冷凍コロッケ" msg:num "個の注文を受けました．"))
    (self:msgbox:popAll)))


(define (factory:order ctx ::Market self ::Factory)
  (define factory-orders (make ArrayList '(200 200 200 200 200 200 200 200 200 200)))
  (ui:request-input self:name
    (ui:form
      (to-string
        ctx:roundnum "日目です．" self:name "さん，農場へじゃがいもを発注して下さい．発注したものは，明日に納品されます．")
      (ui:val-input "個数(ジャガイモ)" 'potato (factory-orders ctx:roundnum)))
    (lambda (potato)
      (self:order potato)
      (manager:send-message 'Farmer (make PotatoOrder self:name potato)))))

(define (factory:delivery ctx ::Market self ::Factory)
  (define stock-before-delivery self:stock)
  (for-each
    (lambda (shop-name ::symbol)
      (define delivery (self:delivery ctx shop-name stock-before-delivery))
      (ui:show-message self:name
        ctx:roundnum "日目です．" shop-name "に冷凍コロッケ" delivery "個を納品しました．")
      (manager:send-message shop-name (make CroquetteDelivery self:name delivery))) *shop-names*))


(define (factory:receive-delivery ctx ::Market self ::Factory)
  (define msg ::PotatoDelivery (self:msgbox:pop))
  (self:receiveDelivery msg:num)
  (ui:show-message self:name ctx:roundnum "日目です．" "じゃがいも" msg:num "個が納品されました．"))

(define (farmer:init ctx ::Market self ::Farmer)
  (self:init))
  
(define (farmer:receive-order ctx ::Market self ::Farmer)
  (define msg ::PotatoOrder (self:msgbox:pop))
  (self:receiveOrder msg:num)
  (ui:show-message self:name  ctx:roundnum "日目です．" "じゃがいも" msg:num "個の注文を受けました．"))

(define (farmer:delivery ctx ::Market self ::Farmer)
  (manager:send-message 'Factory (make PotatoDelivery self:name (self:delivery ctx))))
