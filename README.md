![Telegraff](docs/logo.png "Logo")

<!-- Описание -->

## Подключение

Gradle: 

```
compile("me.ruslanys:telegraff-starter:0.1.0")
```

Maven:

```
<dependency>
    <groupId>me.ruslanys</groupId>
    <artifactId>telegraff-starter</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Настройка

```
telegram.access-key=
telegram.mode= # polling, webhook
telegram.webhook-base-url=
telegram.webhook-endpount-url=
```

## Использование

```kotlin
val exampleHandler = handler {
    commands("/taxi")
    
    step<String> {
        key("locationFrom")
        
        question { 
            MarkdownMessage("Откуда поедем?")
        }
    }
    
    step<String> {
        key("locationTo")
        
        question { 
            MarkdownMessage("Куда поедем?")
        }
    }
    
    step<PaymentMethod> {
        key("paymentMethod")
        
        question { 
            MarkdownMessage("Оплата картой или наличкой?", "Картой", "Наличкой")
        }
        
        validation { 
            when (it) {
                "Картой" -> PaymentMethod.CARD
                "Наличкой" -> PaymentMethod.CASH
                else -> throw ValidationException()
            }
        }
    }
    
    processor { state, answers -> 
        val from = answers["locationFrom"] as String
        val to = answers["locationTo"] as String
        val paymentMethod = answers["paymentMethod"] as PaymentMethod
        
        // TODO: Business logic
        
        MarkdownMessage("Заказ принят. Поедем из $from в $to. Оплата $paymentMethod.")
    }
}

enum class PaymentMethod {
    CARD, CASH
}
```

## Устройство

![Обработка сообщений](docs/processing-diagram.png "Обработка сообщений")