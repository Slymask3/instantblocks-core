#!/bin/bash

VERSION="0.1.0"
[[ "$1" != "" ]] && VERSION="$1"

MC_VERSION="1.19.2"
[[ "$2" != "" ]] && MC_VERSION="$2"

mvn install:install-file \
   -Dfile="fabric/build/libs/instantblocks-core-$VERSION-fabric-$MC_VERSION.jar" \
   -Djavadoc="fabric/build/libs/instantblocks-core-$VERSION-fabric-$MC_VERSION-javadoc.jar" \
   -Dsources="fabric/build/libs/instantblocks-core-$VERSION-fabric-$MC_VERSION-sources.jar" \
   -DgroupId="com.slymask3.instantblocks.core" \
   -DartifactId="instantblocks-core" \
   -Dversion="$VERSION-fabric-$MC_VERSION" \
   -Dpackaging="jar" \
   -DgeneratePom=true

mvn install:install-file \
   -Dfile="forge/build/libs/instantblocks-core-$VERSION-forge-$MC_VERSION.jar" \
   -Djavadoc="forge/build/libs/instantblocks-core-$VERSION-forge-$MC_VERSION-javadoc.jar" \
   -Dsources="forge/build/libs/instantblocks-core-$VERSION-forge-$MC_VERSION-sources.jar" \
   -DgroupId="com.slymask3.instantblocks.core" \
   -DartifactId="instantblocks-core" \
   -Dversion="$VERSION-forge-$MC_VERSION" \
   -Dpackaging="jar" \
   -DgeneratePom=true

mvn install:install-file \
   -Dfile="common/build/libs/instantblocks-core-$VERSION-common-$MC_VERSION.jar" \
   -Djavadoc="common/build/libs/instantblocks-core-$VERSION-common-$MC_VERSION-javadoc.jar" \
   -Dsources="common/build/libs/instantblocks-core-$VERSION-common-$MC_VERSION-sources.jar" \
   -DgroupId="com.slymask3.instantblocks.core" \
   -DartifactId="instantblocks-core" \
   -Dversion="$VERSION-common-$MC_VERSION" \
   -Dpackaging="jar" \
   -DgeneratePom=true
