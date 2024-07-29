 abstract class document {
    public abstract void open();
    public abstract void save();
    public abstract void close();
}
 class WordDocument extends document {
    @Override
    public void open() {
        System.out.println("Opening Word document...");
    }

    @Override
    public void save() {
        System.out.println("Saving Word document...");
    }

    @Override
    public void close() {
        System.out.println("Closing Word document...");
    }
}

// PdfDocument.java
 class PdfDocument extends document {
    @Override
    public void open() {
        System.out.println("Opening PDF document...");
    }

    @Override
    public void save() {
        System.out.println("Saving PDF document...");
    }

    @Override
    public void close() {
        System.out.println("Closing PDF document...");
    }
}

// ExcelDocument.java
 class ExcelDocument extends document {
    @Override
    public void open() {
        System.out.println("Opening Excel document...");
    }

    @Override
    public void save() {
        System.out.println("Saving Excel document...");
    }

    @Override
    public void close() {
        System.out.println("Closing Excel document...");
    }
}
// DocumentFactory.java
 abstract class DocumentFactory {
    public abstract document createDocument();
}
// WordDocumentFactory.java
class WordDocumentFactory extends DocumentFactory {
    @Override
    public document createDocument() {
        return new WordDocument();
    }
}

// PdfDocumentFactory.java
 class PdfDocumentFactory extends DocumentFactory {
    @Override
    public document createDocument() {
        return new PdfDocument();
    }
}

// ExcelDocumentFactory.java
class ExcelDocumentFactory extends DocumentFactory {
    @Override
    public document createDocument() {
        return new ExcelDocument();
    }
}
// FactoryMethodTest.java
