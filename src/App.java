import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final String applicantList = "applicantList.txt";

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        login(input);
        input.close();
    }

    public static void login(Scanner input) {
        String[][] users = {
                { "1", "wisnu@gmail.com", "wisnu123", "Wisnu Saputra", "Universitas Indonesia",
                        "Fakultas Ilmu Komputer", "3.90" },
                { "2", "nana@gmail.com", "nana123", "Nana Casmana", "Universitas Padjajaran", "Fakultas Agama Islam",
                        "2.78" },
                { "3", "jamaludin@gmail.com", "jamal123", "Jamaludin Malik", "Universitas Brawijaya",
                        "Fakultas Peternakan", "3.12" },
                { "4", "marpuah@gmail.com", "marpuah123", "Marpuah Diasti", "Universitas Pakuan Bogor",
                        "Fakultas Teknik", "3.45" },
                { "5", "sukinem@gmail.com", "suki123", "Sukinah Marinah", "Unviersitas Pendidikan Indonesia",
                        "Fakultas Seni", "2.88" },
                { "6", "amartek@gmail.com", "amartek123", "PT Amartek" },
        };

        System.out.print("Masukan email anda\t: ");
        String email = input.nextLine();
        System.out.print("Masukan password anda\t: ");
        String password = input.nextLine();

        String result = checkValidate(users, email, password);
        String userId = result;
        if (result.equals("Email atau password salah!")) {
            System.out.println(result);
            System.out.println("Login Failed");
            return;
        } else {
            System.out.println("Login Berhasil");
        }

        System.out.println("\n--SILAHKAN PILIH MENU DI BAWAH INI--");
        System.out.println("1. Lamar Pekerjaan");
        System.out.println("2. Menampilkan Daftar Pelamar");
        System.out.println("3. Manage Status Pelamar");

        System.out.print("\nSilahkan masukan pilihan anda\t: ");
        int chooseMenu = input.nextInt();
        input.nextLine();

        switch (chooseMenu) {
            case 1:
                applyJob(input, users, userId);
                break;
            case 2:
                showApplicant();
                break;
            case 3:
                manageStatus(input, users);
                break;
            default:
                System.out.println("Pilihan anda tidak ada!");
                break;
        }

    }

    public static String checkValidate(String[][] data, String email, String password) {
        String result = "Email atau password salah!";
        for (int i = 0; i < data.length; i++) {
            if (email.equals(data[i][1])) {
                if (password.equals(data[i][2])) {
                    result = data[i][0];
                    break;
                } else {
                    result = "Email atau password salah!";
                    break;
                }
            }
        }

        return result;
    }

    public static String[][] UsersList(String[][] users, String userId) {
        List<String[]> UserDetail = new ArrayList<>();
        for (int i = 0; i < users.length; i++) {
            if (users[i][0].equals(userId)) {
                UserDetail.add(users[i]);
            }
        }

        String[][] UserArray = new String[UserDetail.size()][];
        UserDetail.toArray(UserArray);

        return UserArray;
    }

    public static void applyJob(Scanner input, String[][] users, String userId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(applicantList, true))) {
            String status = "waiting";
            String[][] vacancies = {
                    { "1", "Frontend Developer", "PT Amartek" },
                    { "2", "Backend Developer", "PT Amartek" },
                    { "3", "Data Analyst", "PT Amartek" },
                    { "4", "Peternak Amuba", "PT Amartek" },
                    { "5", "Pelatih Lumba-Lumba", "PT Amartek" },
            };

            System.out.println("\n-- SELAMAT DATANG DI LINKING --");

            for (int i = 0; i < vacancies.length; i++) {
                System.out.println((i + 1) + ". " + vacancies[i][1] + " - " + vacancies[i][2]);
            }

            System.out.print("\nMasukan pekerjaan yang ingin kamu lamar\t: ");
            int numbers = input.nextInt();
            input.nextLine();

            String result = checkJob(numbers, vacancies);
            if (!result.isEmpty()) {
                System.out.println(result);
                return;
            }

            String[][] usersList = UsersList(users, userId);
            int idCounter = 1;

            if (usersList.length > 0) {
                System.out.println("\n-- HASIL LAMARAN PENGGUNA --");
                for (int i = 0; i < usersList.length; i++) {
                    String uniqeId = String.valueOf(idCounter);
                    System.out.println("Nama\t\t\t: " + usersList[i][3]);
                    System.out.println("Universitas\t\t: " + usersList[i][4]);
                    System.out.println("Fakultas\t\t: " + usersList[i][5]);
                    System.out.println("IPK\t\t\t: " + usersList[i][6]);

                    writer.write(uniqeId + "," + usersList[i][3] + "," + usersList[i][4] + "," + usersList[i][5] + ","
                            + usersList[i][6]
                            + "," + vacancies[numbers - 1][0] + "," + vacancies[numbers - 1][1] + ","
                            + vacancies[numbers - 1][2] + "," + status);
                    writer.newLine();

                    idCounter++;
                }
            } else {
                System.out.println("Tidak ada pengguna dengan ID : " + userId);
            }

            System.out.println("Pekerjaan\t\t: " + vacancies[numbers - 1][1]);
            System.out.println("Perusahaan\t\t: " + vacancies[numbers - 1][2]);
            System.out.println("Status\t\t\t: " + status);

            System.out.println("\nData lamaran berhasil disimpan");
        } catch (IOException e) {
            System.out.println("Opss terjadi kesalahan gess: " + e.getMessage());
        }
    }

    public static String checkJob(int numbers, String[][] data) {
        String result = "";
        if (numbers < 1 || numbers > data.length) {
            result = "Pekerjaan yang anda pilih tidak tersedia!";
        }

        return result;
    }

    public static void showApplicant() {
        String[][] applicant = convertToArray();

        if (applicant != null) {
            System.out.println("\n-- LIST APPLICANT AMARTEK --");
            for (String[] apps : applicant) {
                System.out.println("\nNama\t\t: " + apps[1]);
                System.out.println("Universitas\t: " + apps[2]);
                System.out.println("Fakultas\t: " + apps[3]);
                System.out.println("IPK\t\t: " + apps[4]);
                System.out.println("Pekerjaan\t: " + apps[6]);
                System.out.println("Perusahaan\t: " + apps[7]);
                System.out.println("Status\t\t: " + apps[8]);
            }
        } else {
            System.out.println("Tidak ada data yang tersedia");
        }
    }

    public static void manageStatus(Scanner input, String[][] users) {
        String[][] statusApplicant = convertToArray();

        for (int i = 0; i < statusApplicant.length; i++) {
            System.out.println((i + 1) + ". " + String.join(" - ", statusApplicant[i]));
        }

        System.out.print("\nMasukan nomor pelamar\t: ");
        int numbers = input.nextInt();
        input.nextLine();

        String result = cekChoice(statusApplicant, numbers);
        if (!result.isEmpty()) {
            System.out.println(result);
            return;
        }

        String cekResult = cekAuthorization(users);
        if (!cekResult.isEmpty()) {
            System.out.println(cekResult);
            return;
        }

        System.out.print("\nMasukan status pelamar\t: ");
        String status = input.nextLine();

        statusApplicant[numbers - 1][6] = status;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(applicantList))) {
            for (String[] newApplicant : statusApplicant) {
                writer.write(String.join(",", newApplicant));
                writer.newLine();
            }
            System.out.println("Status berhasil diupdate");
        } catch (IOException e) {
            System.out.println("Opss terjadi kesalahan: " + e.getMessage());
        }
    }

    public static String cekChoice(String[][] data, int numbers) {
        String result = "";
        if (numbers < 1 || numbers > data.length) {
            result = "Pelamar yang anda pilih tidak tersedia!";
        }

        return result;
    }

    public static String cekAuthorization(String[][] users) {
        String result = "";
        boolean hasAccess = false;

        for (int i = 0; i < users.length; i++) {
            if (users[i][0].equals("6")) {
                hasAccess = true;
                break;
            }
        }

        if (!hasAccess) {
            result = "Anda tidak memiliki akses untuk mengupdate status pelamar ini";
        }

        return result;
    }

    public static String[][] convertToArray() {
        List<String[]> ListApplicant = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(applicantList))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] applicant = line.split(",");
                ListApplicant.add(applicant);
            }
        } catch (IOException e) {
            System.out.println("Opss terjadi kesalahan: " + e.getMessage());
            return null;
        }

        return ListApplicant.toArray(new String[0][]);
    }

}
