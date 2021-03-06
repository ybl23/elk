# Eclipse Layout Kernel (ELK)

Diagrams and visual languages are a great thing,
but getting the layout just right for them to be easily understandable
can be tedious and time-consuming.
The Eclipse Layout Kernel provides a number of layout algorithms
as well as an Eclipse-based infrastructure to connect them
to editors and viewers.
The layout algorithms are plain Java
and can thus also be used outside of Eclipse.

## More Info

* [The ELK homepage](http://www.eclipse.org/elk)
* [Our GitHub repository](https://github.com/eclipse/elk/)


## Repository Structure

The repository's structure is pretty straightforward. We only have a few folders:

* `build`:
  Contains all the files necessary to build ELK in all its different forms.
* `config`:
  Contains configuration files, such as our Checkstyle configuration.
* `docs`:
  Contains documentation in the form of a [Hugo](https://gohugo.io/) site.
* `features`:
  Contains all the Eclipse features ELK consists of.
* `plugins`:
  Contains all the plugins ELK consists of.
* `setups`:
  Contains our Oomph setup files.
* `tests`:
  Contains unit tests.


## Building ELK

Information on how to build ELK and the documentation can be found [on our website](https://www.eclipse.org/elk/documentation/contributors/buildingelk.html).
