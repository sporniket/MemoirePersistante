#Sporniket Memoire Persistante (Lasting memory) Photo Manager

_Memoire Persistante_ is a photo manager and photo book creator, that follows these guidelines :

* NO ALTERING of the original files.
* NO MANDATORY PHOTO ORGANISATION, one album = all the photos directly in one folder
* User defined metadata are added into an album descriptor using RDF format.
* Photobook are also RDF files, in fact an excerpt of the selected photos from at least one album.

```
    Sporniket Memoire Persistante is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Sporniket Memoire Persistante is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
```

# How to use in your project with maven

## Build and install the library
To get the latest available code, clone the git repository, build and install to the maven local repository.

```
git clone https://github.com/sporniket/MemoirePersistante.git
cd MemoirePersistante/...
git checkout version_to_use FIXME IF APPLIABLE
mvn install
```

## Add a dependency to your project
Add the needed dependencies in your pom file :

```
<dependency>
	<groupId>com.sporniket.XXX</groupId>
	<artifactId>sporniket-XXX</artifactId>
	<version><!-- the version to use --></version>
</dependency>
```
