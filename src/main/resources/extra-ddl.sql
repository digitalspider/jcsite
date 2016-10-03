DELETE FROM jcloud.product;
INSERT INTO jcloud.product (id,status,name,description,type,cost_price,list_price,sale_price,currency,cdate,mdate) values 
(1,1,'FREE','FREE Linux Container<br/>RAM: 128MB<br/>DISK: 1GB<br/>OS: Apline Edge<br/>Arch: 64-bit<br/>Java: 7<br/>Apache: 2.4','SERVER FREE',0.00,0.00,0.00,'AUD',now(),now()),
(2,1,'LITE','LITE Linux Container<br/>RAM: 1GB<br/>DISK: 10GB<br/>OS: Apline Edge<br/>Arch: 64-bit<br/>Java: 7<br/>Apache: 2.4','SERVER',10.00,10.00,10.00,'AUD',now(),now()),
(3,1,'STANDARD','STANDARD Linux Container<br/>RAM: 2GB<br/>DISK: 25GB<br/>OS: Ubuntu 16.04<br/>Arch: 64-bit<br/>Java: 8<br/>Apache: 2.4','SERVER',20.00,20.00,20.00,'AUD',now(),now()),
(4,1,'LARGE','LARGE Linux Container<br/>RAM: 4GB<br/>DISK: 25GB<br/>OS: Ubuntu 16.04<br/>Arch: 64-bit<br/>Java: 8<br/>Apache: 2.4','SERVER',40.00,40.00,40.00,'AUD',now(),now()),
(5,1,'CUSTOM','CUSTOM Linux Container<br/>RAM: 4GB<br/>DISK: 25GB<br/>OS: Ubuntu 16.04<br/>Arch: 64-bit<br/>Java: 8<br/>Apache: 2.4','SERVER',40.00,40.00,40.00,'AUD',now(),now());
