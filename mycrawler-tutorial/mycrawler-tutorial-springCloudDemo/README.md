#problem palyback#

1. Start CloudEureka(port:8761)
2. Start CloudHystrix with profile 'profile1'
3. Start CloudHystrix with profile 'profile2'
4. Start CloudHystrix with profile 'profile3'
5. Start CloudRibbon(port:8085)
6. open http://localhost:8085/helloRibbon2 (every time you access this url you will get a message contains the port of the CloudHystrix instance called by ribbon)
7. stop one of the CloudHystrix instance and access the url mentioned in step 6 again

repeat step 7 ，you will get the error message

# SpringCloudDemo
<br/>
This repository is test for  the retry of spring ribbon.<br/>
<br/>
You need start CloudEureka , CloudHystrix and CloudRibbon.<br/>
CloudHystrix have 3 profiles, you can start 3 instance use each of them.<br/>
CloudRibbon calls the service supplied by CloudHystrix.<br/>


