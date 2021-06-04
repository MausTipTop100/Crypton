# Crypton
<h3>Welcome to Crypton!</h3>

<h1>Crypton Manual</h1>
<h2>Encryption Manual</h2>
<h3>Arguments</h3>

<table>
  <tr>
    <th>Argument</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>--encrypt-file</td>
    <td>Adds an encryption task for a file</td>
  </tr>
  <tr>
    <td>--decrypt-file</td>
    <td>Adds a decryption task for a file</td>
  </tr>
  <tr>
    <td>--passphrase</td>
    <td>Sets the passphrase for your en- and decryption tasks.</td>
  </tr>
</table>

<h3>Samples</h3>
<h5>Encrypting files</h5>

```
  crypton --encrypt-file "C:/path/to/my/file.txt" --passphrase "my secret passphrase"
  test
```

<p>This will encrypt the entered file with the passphrase my secret passphrase</p>
<h5>Decrypting files</h5>
```shell
crypton --decrypt-file "C:/path/to/my/file.txt.encf" --passphrase "my secret passphrase"
```
<p>
This will decrypt the entered file with the passphrase my secret passphrase
  <b>This will throw an InvalidKeyException of the password is invalid</b>
</p>
