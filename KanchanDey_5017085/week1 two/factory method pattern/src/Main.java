// Step 2: Define Document Interfaces

// Interface for Document
interface Document {
    void open();
    void save();
}

// Interface for Word Document
interface WordDocument extends Document {
    void edit();
}

// Interface for PDF Document
interface PdfDocument extends Document {
    void annotate();
}

// Interface for Excel Document
interface ExcelDocument extends Document {
    void calculate();
}

// Step 3: Create Concrete Document Classes

// Concrete Word Document class
class ConcreteWordDocument implements WordDocument {
    @Override
    public void open() {
        System.out.println("Opening Word document...");
    }

    @Override
    public void save() {
        System.out.println("Saving Word document...");
    }

    @Override
    public void edit() {
        System.out.println("Editing Word document...");
    }
}

// Concrete PDF Document class
class ConcretePdfDocument implements PdfDocument {
    @Override
    public void open() {
        System.out.println("Opening PDF document...");
    }

    @Override
    public void save() {
        System.out.println("Saving PDF document...");
    }

    @Override
    public void annotate() {
        System.out.println("Annotating PDF document...");
    }
}

// Concrete Excel Document class
class ConcreteExcelDocument implements ExcelDocument {
    @Override
    public void open() {
        System.out.println("Opening Excel document...");
    }

    @Override
    public void save() {
        System.out.println("Saving Excel document...");
    }

    @Override
    public void calculate() {
        System.out.println("Calculating Excel document...");
    }
}

// Step 4: Implement the Factory Method

// Abstract Document Factory class
abstract class DocumentFactory {
    public abstract Document createDocument();
}

// Concrete Factory for Word Document
class WordDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new ConcreteWordDocument();
    }
}

// Concrete Factory for PDF Document
class PdfDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new ConcretePdfDocument();
    }
}

// Concrete Factory for Excel Document
class ExcelDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new ConcreteExcelDocument();
    }
}

// Step 5: Test the Factory Method Implementation

 class FactoryMethodPatternExample {
    public static void main(String[] args) {
        // Create Word Document
        DocumentFactory wordFactory = new WordDocumentFactory();
        Document wordDoc = wordFactory.createDocument();
        wordDoc.open();
        ((WordDocument) wordDoc).edit();
        wordDoc.save();

        System.out.println();

        // Create PDF Document
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        Document pdfDoc = pdfFactory.createDocument();
        pdfDoc.open();
        ((PdfDocument) pdfDoc).annotate();
        pdfDoc.save();

        System.out.println();

        // Create Excel Document
        DocumentFactory excelFactory = new ExcelDocumentFactory();
        Document excelDoc = excelFactory.createDocument();
        excelDoc.open();
        ((ExcelDocument) excelDoc).calculate();
        excelDoc.save();
    }
}
