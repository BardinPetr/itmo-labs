import org.gradle.api.file.Directory

import java.security.MessageDigest

class HashHelper {

    private static def md5 = MessageDigest.getInstance("MD5")
    private static def sha = MessageDigest.getInstance("SHA-1")
    private static def base64 = Base64.getEncoder()

    static String generateManifestHashes(Directory classesDir) {
        return classesDir
                .asFileTree
                .files
                .collect {
                    def contents = it.bytes
                    def relativePath = it.path
                            .replace(classesDir.toString(), "")
                            .stripMargin('/')

                    md5.reset()
                    def md5Value = base64.encodeToString(md5.digest(contents))
                    sha.reset()
                    def shaValue = base64.encodeToString(sha.digest(contents))


                    return "Name: ${relativePath}\n" +
                            "Digest-Algorithms: MD5 SHA1\n" +
                            "MD5-Digest: ${md5Value}\n" +
                            "SHA1-Digest: ${shaValue}"
                }
                .join("\n\n")
    }
}
