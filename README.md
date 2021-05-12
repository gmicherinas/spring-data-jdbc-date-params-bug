It is a project about describing a possible regression in spring-data-jdbc when upgrading from 1.x to 2.x

How to reproduce
---
First execute tests in `SpringDataJdbcDateParamsBugApplicationTests` as is (with `spring-boot-starter-parent` 2.2.13.RELEASE which is bundled with `spring-data-jdbc` 1.1.12.RELEASE) 

The 2 tests should be green.

Then change the `spring-boot-starter-parent` version to a 2.3.x or 2.4.x one, which are bundled with a 2.x version of `spring-data-jdbc`

The `given_nativeUpdateQuer_WithLocaDateTimeParam_WithoutCast_ShouldWork` should now fail.

References:
---
 - [StackOverflow question](https://stackoverflow.com/questions/67503013/after-upgrading-spring-data-jdbbc-from-1-1-12-release-to-2-0-6-release-localdate)
 - [Spring-data-jdbc issue]()