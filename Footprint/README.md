# Footprint
[programming style](https://jervyshi.gitbooks.io/google-java-styleguide-zh/content/)

### Develop Infomation
- Login Account for testing: 
```
  email(username): 123@123.com 
  password:        123456
```
- Automatically login when clicking login btn, for development. The code below is in LoginActivity.java
```
//-----------------automatically fill in email and pwd to testing-------------
//TODO: remember to delete before publishing
  textEmail.setText("123@123.com");
  textPwd.setText("123456");
//----------------------------------------------------------------------------
```
### Details about programming style
- Use lower camel case when declaring a variable. Example: appleCare.
- ...

### Bug...
- ~~The app crash if quickly clike map after clicking account.
