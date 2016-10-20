(define (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
   (def:player name: 'Farmer type: 'agent class: Farmer)
   (def:player name: 'Factory type: 'human class: Factory)
   (def:player name: 'Shop1 type: 'human class: Shop)
   (def:player name: 'Shop2 type: 'human class: Shop)))
