package:clean
	@mvn package

clean:
	@mvn clean

install:clean
	@mvn install

test:package
	@mvn test -Ptest

coverage:test
	@mvn clean verify -Ptest
	@open kunbase-test/target/site/jacoco-aggregate/index.html

prepare-jar:install
	@cp -f kunbase-example/order-center-bp-ka/target/order-center-bp-ka-0.0.1.jar ~
	@cp -f kunbase-example/order-center-bp-isv/target/order-center-bp-isv-0.0.1.jar ~

javadoc:install
	@mvn javadoc:javadoc
	@open target/site/apidocs/index.html

release-javadoc:install
	@git checkout gh-pages
	@git pull
	@git checkout master
	@mvn javadoc:javadoc
	@git checkout gh-pages
	@rm -rf doc/apidocs
	@mv -f target/site/apidocs/ doc/
	@git add doc/apidocs
	@git commit -m 'Javadoc updated' doc/apidocs
	@git push
	git checkout master
