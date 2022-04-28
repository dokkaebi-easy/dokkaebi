import { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import Stack from '@mui/material/Stack';
import FormHelperText from '@mui/material/FormHelperText';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import RepositoryModal from 'Components/Pages/Setting/GitLabPage/RepositoryModal/RepositoryModal';
import { Git } from 'Components/MDClass/GitData/GitData';
import axios from 'axios';
import { useStore } from 'Components/Store/DropDownStore/DropDownStore';
import { ResponseIdName } from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';

interface GitProps {
  gitData: Git;
}

export default function GitLabRepositories({ gitData }: GitProps) {
  const dropDownItems = useStore((state) => state.account);
  const setDropDownItems = useStore((state) => state.setAccount);

  const [projectID, setProjectID] = useState(gitData.gitProjectId);
  const [repositoryURL, setRepositoryURL] = useState(gitData.repositoryUrl);
  const [branchName, setBranchName] = useState(gitData.branchName);
  const [account, setAccount] = useState('');
  const [accounts, setAccounts] = useState<string[]>([]);
  const [open, setOpen] = useState(false);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleItemClickProps = (index: number) => {
    setAccount(accounts[index]);
    gitData.accountId = index + 1;
  };

  const handleProjectIDChange = (event: any) => {
    setProjectID(event.target.value);
    gitData.gitProjectId = Number(event.target.value);
  };

  const handleRepositoryURLChange = (event: any) => {
    setRepositoryURL(event.target.value);
    gitData.repositoryUrl = event.target.value;
  };

  const handleBranchNameChange = (event: any) => {
    setBranchName(event.target.value);
    gitData.branchName = event.target.value;
  };

  const handleAxiosProps = (data: ResponseIdName[]) => {
    const arr = data.map((value) => value.name);
    setAccounts([...arr]);
    setAccount(arr[gitData.accountId - 1]);
  };

  useEffect(() => {
    axios.get('/api/git/accounts').then((res) => {
      const data = res.data as ResponseIdName[];
      setDropDownItems([...data]);
      const arr = data.map((value) => value.name);
      setAccounts([...arr]);
      setAccount(arr[gitData.accountId - 1]);
    });

    return () => {
      setAccounts([]);
    };
  }, []);

  return (
    <Box my={3}>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper
          sx={{
            padding: 1,
            textAlign: 'center',
            width: 170,
            color: ' white',
            background: 'linear-gradient(195deg, #666, #191919)',
          }}
        >
          Repositories
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3 }}>
          <Grid container spacing={2}>
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography>Project ID</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                id="outlined-basic"
                label="Project ID"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="Project ID"
                defaultValue={projectID}
                onChange={handleProjectIDChange}
              />
              <FormHelperText id="component-helper-text">
                (※ only number)
              </FormHelperText>
            </Grid>
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography>Repository URL</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                id="outlined-basic"
                label="Repository URL"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="Repository URL"
                defaultValue={repositoryURL}
                onChange={handleRepositoryURLChange}
              />
            </Grid>
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography>Credentials</Typography>
            </Grid>
            <Grid item xs={10}>
              <Stack direction="row" spacing={2} alignItems="center">
                <SelectItem
                  defaultValue={account}
                  label="Credentials"
                  Items={accounts}
                  Click={handleItemClickProps}
                />
                <Button
                  variant="outlined"
                  startIcon={<AddIcon />}
                  size="small"
                  onClick={handleOpen}
                  sx={{ color: 'black', borderColor: 'black' }}
                >
                  Add
                </Button>
                <RepositoryModal
                  open={open}
                  Close={handleClose}
                  Change={handleAxiosProps}
                />
              </Stack>
            </Grid>
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography>Branch Specifier</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                id="outlined-basic"
                label="Branch Specifier"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="Branch Specifier"
                defaultValue={branchName}
                onChange={handleBranchNameChange}
              />
            </Grid>
          </Grid>
        </Paper>
      </Box>
    </Box>
  );
}
