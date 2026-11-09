# HackClient (Fabric, 1.21.11)

## Easiest: build online with GitHub (nothing to install)
1. Make a free GitHub account, create a new repository, click "uploading an existing file"
   and drag in everything from this folder (keep the .github folder).
2. Open the repo's Actions tab -> "build" -> wait ~3 minutes.
3. Open the finished run, download the "hackclient-jar" artifact, unzip it.
   Use the jar WITHOUT "-sources" in its name.
4. Put it in .minecraft/mods with Fabric Loader + Fabric API for 1.21.11.

## Local build
JDK 21, then `gradle build` (or add the wrapper from the Fabric example mod and run ./gradlew build).
Jar appears in build/libs/.

## Use
Keys (no screen open): R = ESP, B = BaseFinder, V = AutoSprint, G = Fullbright
Chat: .modules | .toggle <name> | .esp <players|hostiles|animals|items>
