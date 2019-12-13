package igor.polyglot;

import org.graalvm.polyglot.Context;

public class HelloPolyglot {
    public static void main(String[] args) {
        System.out.println("Hello Java!");
        try (Context context = Context.create()) {
            context.eval("python", "message = 'The Magic Words are still Squeamish Ossifrage'\n" +
                    "\n" +
                    "bob_pem = '''\n" +
                    "-----BEGIN PUBLIC KEY-----\n" +
                    "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEHiG0sllsW2K9uX/Ey1nxJsv4u/1z\n" +
                    "28JgocZcuFcmE/BuKXZ1w5CB35VxrYqF6RKUucnaauk4VfjSAfYr6gC+GA==\n" +
                    "-----END PUBLIC KEY-----'''\n" +
                    "\n" +
                    "\n" +
                    "import binascii, base64\n" +
                    "from cryptography.hazmat.backends import default_backend\n" +
                    "from cryptography.hazmat.primitives import hashes\n" +
                    "from cryptography.hazmat.primitives.asymmetric import ec\n" +
                    "from cryptography.hazmat.primitives.serialization import Encoding, PublicFormat, " +
                    "load_pem_public_key\n" +
                    "from cryptography.hazmat.primitives.kdf.x963kdf import X963KDF\n" +
                    "from cryptography.hazmat.primitives.ciphers.aead import AESGCM\n" +
                    "\n" +
                    "backend = default_backend()\n" +
                    "\n" +
                    "bob_public = load_pem_public_key(bob_pem, backend)\n" +
                    "bob_pub_bytes = bob_public.public_bytes(Encoding.DER, PublicFormat.SubjectPublicKeyInfo)[-65:]\n" +
                    "\n" +
                    "alice_priv = ec.generate_private_key(ec.SECP256R1(), backend)\n" +
                    "alice_pub_bytes = alice_priv.public_key().public_bytes(Encoding.DER, PublicFormat" +
                    ".SubjectPublicKeyInfo)[-65:]\n" +
                    "\n" +
                    "shared_key = alice_priv.exchange(ec.ECDH(), bob_public)\n" +
                    "\n" +
                    "xkdf = X963KDF(\n" +
                    "    algorithm=hashes.SHA256(),\n" +
                    "    length=16,\n" +
                    "    sharedinfo=alice_pub_bytes,\n" +
                    "    backend=backend\n" +
                    ")\n" +
                    "key_enc = xkdf.derive(shared_key)\n" +
                    "\n" +
                    "iv = binascii.a2b_hex('00000000000000000000000000000000')\n" +
                    "\n" +
                    "C = AESGCM(key_enc)\n" +
                    "ct = C.encrypt(iv, message, \"\") \n" +
                    "\n" +
                    "final_ct = alice_pub_bytes  + ct\n" +
                    "\n" +
                    "print \"\\nFinal message, Base-64 Encoded, to drop back into the demo app:\\n\"\n" +
                    "print base64.b64encode(final_ct)");
        }
    }
}
