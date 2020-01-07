FROM store/intersystems/iris:2019.1.0.511.0-community


# ubuntu-openjdk-8-jdk #

    RUN apt-get update && \
        apt-get install -y openjdk-8-jdk && \
        apt-get install -y ant && \
        apt-get clean && \
        rm -rf /var/lib/apt/lists/* && \
        rm -rf /var/cache/oracle-jdk8-installer;

    # Setup JAVA_HOME, this is useful for docker commandline
    ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
    RUN export JAVA_HOME

# ubuntu-openjdk-8-jdk #

WORKDIR /opt/app

COPY ./src/ ./src/

 # Download jars
RUN mkdir -p /opt/app/jars && cd /opt/app/jars \
&& wget -q -O - https://api.github.com/repos/gevorg95/test1/releases/latest \
  | egrep 'http*.*jars.tar' \
  | cut -d : -f 2,3 \
  | tr -d '"' \
  | wget -O - -i - > jars.tar \
&& tar -xvvf jars.tar

RUN iris start $ISC_PACKAGE_INSTANCENAME quietly EmergencyId=sys,sys && \
    /bin/echo -e "sys\nsys\n" \
            " do ##class(Security.Users).UnExpireUserPasswords(\"*\")\n" \
            " do ##class(Security.Users).AddRoles(\"admin\", \"%ALL\")\n" \
            " zn \"USER\" \n" \
            " do \$system.OBJ.Load(\"/opt/app/src/oscript/IrisGraph/Installer.cls\",\"ck\")\n" \
            " set pVars(\"Namecpace\") = \"GDB\" \n" \
            " set pVars(\"SourceDir\") = \"/opt/app/src/oscript/\" \n" \
            " do ##class(IrisGraph.Installer).setup(.pVars) \n" \
            " halt" \
    | iris session $ISC_PACKAGE_INSTANCENAME && \
    /bin/echo -e "sys\nsys\n" \
    | iris stop $ISC_PACKAGE_INSTANCENAME quietly
