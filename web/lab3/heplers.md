Init SVN:

```shell
mkdir $HOME/.svnrepos
svnadmin create $HOME/.svnrepos/opi3
svn mkdir -m 'structure init' \
file://$HOME/.svnrepos/opi3/trunk \
file://$HOME/.svnrepos/opi3/branches
```


```shell
rm -rf lab3/.svn && svn checkout file://$HOME/.svnrepos/opi3/trunk lab3
```