# Define the java compiler to use
JAVAC = javac
JAR = jar
SRC_DIR = javaPC
OUT_DIR = $(SRC_DIR) # Define output directory for .class files

# Name of the jar file
JAR_NAME = Parallel-Coordinates-Vis.jar

# Manifest file
MANIFEST = manifest.txt

# Java source files
SOURCES = $(wildcard $(SRC_DIR)/*.java)

# Java class files
CLASSES = $(SOURCES:.java=.class)

.PHONY: all clean

all: $(JAR_NAME)

# Rule to create jar file
$(JAR_NAME): $(CLASSES)
	$(JAR) cvfm $(JAR_NAME) $(MANIFEST) -C . $(SRC_DIR)

# Rule to compile java files
$(SRC_DIR)/%.class: $(SRC_DIR)/%.java
	$(JAVAC) $<

# Conditional clean rule depending on OS
ifeq ($(OS),Windows_NT)
clean:
	del /F /Q $(SRC_DIR)\*.class
	del /F /Q $(JAR_NAME)
else
clean:
	rm -f $(SRC_DIR)/*.class $(JAR_NAME)
endif
