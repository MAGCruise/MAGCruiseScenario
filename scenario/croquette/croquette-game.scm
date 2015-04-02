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
  (def:ext-player 'Factory 'human Factory
    (int[] 1000 1000 1000 1000 1000 1000 1000 1000 1000 1000))

  (def:ext-player 'Shop1 'human Shop
      (int[] 100 100 100 100 100 100 100 100 100 100)
      (int[] 400 400 400 400 400 400 400 400 400 400))
  (def:ext-player 'Shop2 'human Shop
      (int[] 150 150 150 150 150 150 150 150 150 150)
      (int[] 300 300 300 300 300 300 300 300 300 300))

  (def:round
    (def:stage 'init
      (def:task 'Factory 'factory:init)
      (def:players-task *shop-names* 'shop:init))
    (def:parallel-stage 'shop-order-and-pricing-factory-order
      (def:players-task *shop-names* 'shop:order)
      (def:players-task *shop-names* 'shop:pricing)
      (def:stage 'factory-order 
        (def:task 'Factory 'factory:order)
        (def:task 'Farmer 'farmer:receive-order)))
    (def:stage 'factory-receive-order 
      (def:task 'Factory 'factory:receive-order))
    (def:parallel-stage 'closing
      (def:task 'Factory 'factory:closing)
      (def:players-task *shop-names* 'shop:closing)))

  (def:round
    (def:parallel-stage 'status
      (def:players-task *shop-names* 'shop:status)
      (def:task 'Factory 'factory:status))
    (def:restage 'shop-order-and-pricing-factory-order)
    (def:restage 'factory-receive-order)
    (def:stage 'farmer-delivery
      (def:task 'Farmer 'farmer:delivery)
      (def:task 'Factory 'factory:receive-delivery))
    (def:restage 'closing))

  (def:rounds 1
    (def:restage 'status)
    (def:stage 'factory-delivery
      (def:task 'Factory 'factory:delivery)
      (def:players-task *shop-names* 'shop:receive-delivery))
    (def:restage 'shop-order-and-pricing-factory-order)
    (def:restage 'factory-receive-order)
    (def:restage 'farmer-delivery)
    (def:restage 'closing))

  (def:round 
    (def:restage 'status)
    (def:restage 'farmer-delivery)
    (def:restage 'factory-delivery)
    (def:stage 'factory-no-order
      (def:task 'Factory 'factory:no-order))
    (def:parallel-stage 'shop-no-order
      (def:players-task *shop-names* 'shop:no-order))
    (def:parallel-stage 'shop-pricing
      (def:players-task *shop-names* 'shop:pricing))
    (def:restage 'closing))

  (def:round 
    (def:restage 'status)
    (def:restage 'factory-delivery)
    (def:restage 'factory-no-order)
    (def:restage 'shop-no-order)
    (def:restage 'shop-pricing)
    (def:restage 'closing))

  (def:round 
    (def:restage 'status)))

(define (shop:no-order ctx ::Market self ::Shop)
  (self:set 'order-of-croquette 0))

(define (factory:no-order ctx ::Market self ::Factory)
  (self:set 'order-of-potato 0))


(define (shop:status ctx ::Market self ::Shop)
  (ui:show-message self:name 
    (<div-class> "alert alert-info"
      (to-string 
        "<strong>在庫表</strong>"
        (self:tabulateHistory 'delivery 'sales 'stock 'order )
        "<strong>販売表</strong>"
        (self:tabulateHistory 'price 'sales 'earnings 'demand)
        "<strong>収支表</strong>"
        (self:tabulateHistory 'materialCost 'inventoryCost 'earnings 'profit)))))

(define (factory:status ctx ::Market self ::Factory)
  (ui:show-message self:name
    (<div-class> "alert alert-info"
      (to-string "<strong>決算表</strong>"
        (self:tabulateHistory 'orderOfPotato 'deliveredPotato 'orderedCroquette 'demand 'sales
                          'production 'stock 'inventoryCost 'materialCost 'machiningCost 'earnings 'profit)))))

(define (shop:init ctx ::Market self ::Shop)
  (ui:show-message self:name 
    (<div-class> "alert alert-info"
      (to-string "ゲームのはじまりです！"
        (<ul>
          (<li> "発注：" "初日(0日目)に発注した冷凍コロッケは翌々日(2日目)に納品され，その日から販売できます．")
          (<li> "在庫：" "現在の冷凍コロッケの在庫は" self:stock "個です．"))))))

(define (factory:init ctx ::Market self ::Factory)
  (ui:show-message self:name 
    (<div-class> "alert alert-info"
      (to-string "ゲームのはじまりです！"
      (<ul>
        (<li> "受注："
                     "初日(0日目)に受けた注文は翌々日(2日目)に納品しなくてはなりません．"
                     "初日(0日目)に発注したじゃがいもは，翌日(1日目)に農家から納品され，翌々日(2日目)にへショップへ納品できます．")
        (<li> "在庫：" "現在の冷凍コロッケの在庫は" self:stock "個です．"))))))


(define (factory:closing ctx ::Market self ::Factory)
  (self:closing)
  (ui:show-message self:name 
    (<div-class> "alert alert-info"
      (to-string self:name "の" ctx:roundnum "日目の決算です．"
        (<ul>
          (<li> "販売：" self:demand "個の冷凍コロッケの納品が必要でした．" "冷凍コロッケを1個" self:price "円で"
                         self:sales "個納品しました．" "売り上げは" self:earnings "円です．")
          (<li> "発注：" self:orderOfPotato "個のじゃがいもを発注しました．明日に納品されます．")
          (<li> "受注：" self:orderedCroquette "個の冷凍コロッケの注文を受けました．")
          (<li> "生産：" self:deliveredPotato "個のじゃがいもが納品されました．支払額は" self:materialCost "円です．"
                         self:production "個の冷凍コロッケを作成しました．" self:machiningCost "円の生産費がかかりました．"
                         "冷凍コロッケの在庫は" self:stock "個になりました．" "在庫維持費は" self:inventoryCost "円です．"))))))

(define (shop:closing ctx ::Market self ::Shop)
  (self:closing (ctx:distributeDemand self))
  (define other ::Shop (ctx:getOther self))
  (define sale-msg (to-string "コロッケを1個" self:price "円で販売しました．" "お店にはお客さんが" self:demand "人きました．"
                              self:sales "個が売れました．" "売り上げは" self:earnings "円です．"))
  
  (ui:show-message self:name (<div-class> "alert alert-info" sale-msg))

  (ui:show-message self:name
    (<div-class> "alert alert-info"
      (to-string self:name "の" ctx:roundnum "日目の決算です．"
      (<ul>
        (<li> "在庫：" self:delivery "個の冷凍コロッケが納品されました．"
                       self:sales "個のコロッケを売りました．"
                       "在庫は" self:stock "個です．"
                       "また，" self:order "個の冷凍コロッケを発注しました．この冷凍コロッケは明後日に納品予定です．")
        (<li> "販売：" sale-msg)
        (<li> "収支：" "仕入費は" self:materialCost "円，"
                       "在庫費は" self:inventoryCost "円，"
                       "売上高は" self:earnings "円，"
                       "利益は" self:profit "円です．")
        (<li> "競合店：" "競合店は" other:price "円でコロッケを販売し，"  other:demand "人が来店したそうです．"))))))


(define (shop:order ctx ::Market self ::Shop)
  (ui:request-to-input self:name
    (ui:form
      (to-string  (<h5> "発注") ctx:roundnum "日目です．" self:name "さん，コロッケ工場へ発注して下さい．発注したものは，明後日に納品されます．")
      (ui:val-input "個数(冷凍コロッケ)" 'num-of-croquette (self:defaultOrders ctx:roundnum) (Min 0) (Max 1000)))
    (lambda (num-of-croquette ::number)
      (self:order num-of-croquette)
      (ui:show-message self:name (<div-class> "alert alert-success" (to-string "冷凍コロッケを" num-of-croquette "個発注しました．明後日に納品されます．")))
      (manager:send-message 'Factory (make CroquetteOrder self:name num-of-croquette)))))

(define (shop:pricing ctx ::Market self ::Shop)
  (ui:request-to-input self:name
    (ui:form
          (to-string (<h5> "注文価格") ctx:roundnum "日目です．" self:name "さん，今日のコロッケの販売価格を決定して下さい．")
      (ui:val-input "販売価格(コロッケ)" 'price (self:defaultPrices ctx:roundnum) (Min 60) (Max 200)))
    (lambda (price-of-croquette)
      (ui:show-message self:name (<div-class> "alert alert-success" (to-string "コロッケ1個の販売価格を" price-of-croquette "円に決めました．")))
      (self:price price-of-croquette))))

(define (shop:order-and-pricing ctx ::Market self ::Player)
  (shop:order ctx self)
  (shop:pricing ctx self))

(define (shop:receive-delivery ctx ::Market self ::Shop)
  (define msg ::CroquetteDelivery (self:msgbox:pop))
  (self:receiveDelivery msg:num)
  (ui:show-message self:name 
    (<div-class> "alert alert-info"
      (to-string ctx:roundnum "日目です．" "冷凍コロッケが" msg:num "個納品されました．在庫数は" self:stock "個になりました．"))))


(define (factory:receive-order ctx ::Market self ::Factory)
  (for-each
    (lambda (msg ::CroquetteOrder)
      (self:receiveOrder msg)
      (ui:show-message self:name
            (<div-class> "alert alert-info"
              (to-string ctx:roundnum "日目です．" msg:from "から冷凍コロッケ" msg:num "個の注文を受けました．"))))
    (self:msgbox:popAll)))


(define (factory:order ctx ::Market self ::Factory)
  (ui:request-to-input self:name
    (ui:form
      (to-string
        ctx:roundnum "日目です．" self:name "さん，農場へじゃがいもを発注して下さい．発注したものは，明日に納品されます．")
      (ui:val-input "個数(ジャガイモ)" 'potato (self:defaultOrdersToFarmer ctx:roundnum) (Min 0) (Max 1000)))
    (lambda (potato)
      (self:order potato)
      (manager:send-message 'Farmer (make PotatoOrder self:name potato)))))

(define (factory:delivery ctx ::Market self ::Factory)
  (define stock-before-delivery self:stock)
  (define msg (to-string ctx:roundnum "日目です．"))
  (for-each
    (lambda (shop-name ::symbol)
      (define delivery (self:delivery ctx shop-name stock-before-delivery))
      (set! msg (to-string msg (to-string shop-name "に冷凍コロッケ" delivery "個を納品しました．")))
      (manager:send-message shop-name (make CroquetteDelivery self:name delivery)))
    *shop-names*)
  (ui:show-message self:name
      (<div-class> "alert alert-info" msg))
)


(define (factory:receive-delivery ctx ::Market self ::Factory)
  (define msg ::PotatoDelivery (self:msgbox:pop))
  (self:receiveDelivery msg:num)
  (ui:show-message self:name
    (<div-class> "alert alert-info"
      (to-string ctx:roundnum "日目です．" "じゃがいも" msg:num "個が納品されました．"))))

  
(define (farmer:receive-order ctx ::Market self ::Farmer)
  (define msg ::PotatoOrder (self:msgbox:pop))
  (self:receiveOrder msg:num)
  (ui:show-message self:name  ctx:roundnum "日目です．" "じゃがいも" msg:num "個の注文を受けました．"))

(define (farmer:delivery ctx ::Market self ::Farmer)
  (manager:send-message 'Factory (make PotatoDelivery self:name (self:delivery ctx))))
