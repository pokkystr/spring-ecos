<p># rabbitMQ<br />Demo Message Queue from Rabbit MQ</p>
<p>แบ่งรูปแบบของการ Exchange ออกเป็น 3 รูปแบบ</p>
<ul>
<li>Direct exchange &mdash;จะส่ง message ไปยัง Queue โดยตรงด้วย message routing key</li>
<li>Fanout exchange &mdash; จะส่ง message กระจายไปทุก Queue ที่เชื่อมโยงอยู่กัน exchange</li>
<li>Topic exchange &mdash; จะส่ง message ไปยัง Queue โดยขึ้นอยู่กับ matching ระหว่าง message routing key และ pattern ที่เชื่อมโยง Queue กับ Exchange</li>
</ul>
<img src="https://cdn-images-1.medium.com/max/800/1*HoAG-7IhLaXShPJG-g9kvA.png">
<p>ติดตั้ง RabbitMQ Core<br />เราสามารถติดตั้ง RabbitMQ ในเครื่องของเราด้วย ใน MacOS ด้วยคำสั่ง</p>
<p>$ brew update<br />$ brew install rabbitmq</p>
<p>และรัน RabbitMQ server ด้วยคำสั่ง</p>
<p>$ rabbitmq-server</p>

<p>Config yml
spring:
  rabbitmq:
    host: localhost
    port: 5672
</p>    

<p> ##Body Request## </p>
{
  "id": 1,
  "firstName": "pigke",
  "lastName": "kulk",
  "age": "31",
  "email": "pokky_str@mail.com"
}


เรียนรู้ Rabbit MQ in 5 mini
https://drive.google.com/open?id=1j8z7orxx9QIMkJ_hZbNiRZCWU_gJ38mn
