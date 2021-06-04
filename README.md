# Crypton
<h3>Welcome to Crypton!</h3>
<h4>Downloads</h4>
<table>
  <tr>
    <th>OS</th>
    <th>Link</th>
  </tr>
  <tr>
    <td>Windows</td>
    <td><a href="">Download from TheMausCode</a></td>
</table>

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
```

<p>This will encrypt the entered file with the passphrase my secret passphrase</p>
<br/>
<h5>Decrypting files</h5>

```
crypton --decrypt-file "C:/path/to/my/file.txt.encf" --passphrase "my secret passphrase"
```

<p>
This will decrypt the entered file with the passphrase my secret passphrase
<br/>
  <b>(!) This will throw an InvalidKeyException if the password is invalid</b>
</p>

<h2>Installation Manual</h2>
<h3>Windows</h3>

At first you need to download the ZIP file [here]().
Then extract the ZIP file

There are several batch files in the ZIP file.
<table>
  <tr>
    <th>File</th>
    <th>Action</th>
  <tr>
  <tr>
    <td>INSTALL.bat</td>
    <td>Execute to install</td>
  </tr>
  <tr>
    <td>UPDATE.bat</td>
    <td>Execute to re-install / update</td>
  </tr>
  <tr>
    <td>UNINSTALL.bat</td>
    <td>Execute to uninstall</td>
  </tr>
</table>
