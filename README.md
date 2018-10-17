![Telegraff](docs/logo.png "Logo")

<!-- Описание -->

## Подключение

Gradle: 

```
compile("me.ruslanys.telegraff:telegraff-starter:0.1.0")
```

Maven:

```
<dependency>
    <groupId>me.ruslanys.telegraff</groupId>
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
enum class PaymentMethod {
    CARD, CASH
}

handler("/taxi", "такси") {
    step<String>("locationFrom") {
        question {
            MarkdownMessage("Откуда поедем?")
        }
    }

    step<String>("locationTo") {
        question {
            MarkdownMessage("Куда поедем?")
        }
    }

    step<PaymentMethod>("paymentMethod") {
        question {
            MarkdownMessage("Оплата картой или наличкой?", "Картой", "Наличкой")
        }

        validation {
            when (it.toLowerCase()) {
                "картой" -> PaymentMethod.CARD
                "наличкой" -> PaymentMethod.CASH
                else -> throw ValidationException("Пожалуйста, выбери один из вариантов")
            }
        }
    }

    process { state, answers ->
        val from = answers["locationFrom"] as String
        val to = answers["locationTo"] as String
        val paymentMethod = answers["paymentMethod"] as PaymentMethod

        // Business logic

        MarkdownMessage("Заказ принят. Поедем из $from в $to. Оплата $paymentMethod.")
    }
}
```

## Устройство

![Обработка сообщений](docs/processing-diagram.png "Обработка сообщений")