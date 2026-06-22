package tugasCrud

fun displayMenu() {
    println("==================================")
    println(" Menu Utama:")
    println(" 1. Tambah Hewan")
    println(" 2. Lihat Daftar Hewan")
    println(" 3. Ubah Data Hewan")
    println(" 4. Hapus Hewan")
    println(" 5. Cari Hewan")
    println(" 6. Keluar")
    println("----------------------------------")
    print(" Pilihan kamu: ")
}

fun handleAdd(manager: AnimalManager) {
    println("--------Tambah Hewan--------")
    print("Kode: ")
    val code = readLine() ?: ""
    print("Nama: ")
    val name = readLine() ?: ""
    print("Jenis: ")
    val species = readLine() ?: ""

    if (manager.add(code, name, species)) {
        println("Hewan berhasil ditambahkan!\n")
    } else {
        println("Gagal! Kode sudah digunakan atau ada field yang kosong.\n")
    }
}

fun handleList(manager: AnimalManager) {
    println("--------Daftar Hewan--------")
    val list = manager.getAll()
    if (list.isEmpty()) {
        println("Belum ada data hewan.\n")
    } else {
        list.forEachIndexed { index, animal ->
            println("${index + 1}. [${animal.code}] ${animal.name} - ${animal.species}")
        }
        println()
    }
}

fun handleUpdate(manager: AnimalManager) {
    println("--------Ubah Hewan----------")
    print("Masukkan kode hewan yang ingin diubah: ")
    val code = readLine() ?: ""
    print("Nama baru: ")
    val name = readLine() ?: ""
    print("Jenis baru: ")
    val species = readLine() ?: ""

    if (manager.update(code, name, species)) {
        println("Data berhasil diubah!\n")
    } else {
        println("Kode tidak ditemukan.\n")
    }
}

fun handleDelete(manager: AnimalManager) {
    println("--------Hapus Hewan---------")
    print("Masukkan kode hewan yang ingin dihapus: ")
    val code = readLine() ?: ""

    if (manager.delete(code)) {
        println("Hewan berhasil dihapus!\n")
    } else {
        println("Kode tidak ditemukan.\n")
    }
}

fun handleSearch(manager: AnimalManager) {
    println("--------Cari Hewan----------")
    print("Masukkan kode hewan: ")
    val code = readLine() ?: ""
    val animal = manager.findByCode(code)

    if (animal != null) {
        println("Ditemukan -> [${animal.code}] ${animal.name} - ${animal.species}\n")
    } else {
        println("Hewan dengan kode $code tidak ditemukan.\n")
    }
}