 class BuilderPatternExample {

    public static void main(String[] args) {
        Computer computer1 = new Computer.Builder()
                .setCpu("Intel i7")
                .setRam(16)
                .setStorage(512)
                .build();

        Computer computer2 = new Computer.Builder()
                .setCpu("AMD Ryzen 7")
                .setRam(8)
                .setStorage(256)
                .setDisplay("4K")
                .build();

        System.out.println(computer1);
        System.out.println(computer2);
    }

    public static class Computer {

        private final String cpu;
        private final int ram;
        private final int storage;
        private final String display;

        private Computer(Builder builder) {
            this.cpu = builder.cpu;
            this.ram = builder.ram;
            this.storage = builder.storage;
            this.display = builder.display;
        }

        public String getCpu() {
            return cpu;
        }

        public int getRam() {
            return ram;
        }

        public int getStorage() {
            return storage;
        }

        public String getDisplay() {
            return display;
        }

        @Override
        public String toString() {
            return "Computer{" +
                    "cpu='" + cpu + '\'' +
                    ", ram=" + ram +
                    ", storage=" + storage +
                    ", display='" + display + '\'' +
                    '}';
        }

        public static class Builder {
            private String cpu;
            private int ram;
            private int storage;
            private String display;

            public Builder setCpu(String cpu) {
                this.cpu = cpu;
                return this;
            }

            public Builder setRam(int ram) {
                this.ram = ram;
                return this;
            }

            public Builder setStorage(int storage) {
                this.storage = storage;
                return this;
            }

            public Builder setDisplay(String display) {
                this.display = display;
                return this;
            }

            public Computer build() {
                return new Computer(this);
            }
        }
    }
}
