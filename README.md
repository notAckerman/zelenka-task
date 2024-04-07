# zelenka-task

Для запуска необходимо установить:
1. Apache Maven (https://maven.apache.org/download.cgi)
Гайд по установке: https://www.youtube.com/watch?v=km3tLti4TCM
2. JDK 17 и выше (https://www.oracle.com/java/technologies/downloads/#java17)
3. MySQL (https://dev.mysql.com/downloads/installer/)
Гайд по установке: https://www.youtube.com/watch?v=u96rVINbAUI

После того, как были выполненые вышеупомянутые требования, необходимо скачать из этого репозитория book-api и book-control-panel.
По пути book-api/src/main/resources найти файл application.yaml и в нём переопределить параметры, необходимые для подключения к 
MySQL (root, password, url), также в MySQL необходимо создать схему с названием 'books'.

Далее Вам необходимо собрать проекты. Для этого открываем командую строку, прописываем путь до папки book-api и прописываем команду 'mvn clean install', после выполнения этого дейстия в папке target, которая находится в папке book-api, должен появиться файл с расширением .jar. Далее проделываем те же самые действия для book-control-panel.

Для запуска проекта необходимо с помощью команды 'java -jar book-api-0.0.1-SNAPSHOT.jar' запустить файл 'book-api-0.0.1-SNAPSHOT.jar' в папкe book-api/target. Далее проделать аналогичные действия для файла 'book-control-panel-1.0-SNAPSHOT.jar' (Надеюсь догадаетесь изменить название файла в команде java -jar 'Название файла'), который был сгенерирован в папке второго проекта.

Если возникли ошибки или вопросы, то пишите сюда https://t.me/dyightjhons
