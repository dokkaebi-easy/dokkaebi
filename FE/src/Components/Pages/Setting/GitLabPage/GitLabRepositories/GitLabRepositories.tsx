import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import Stack from '@mui/material/Stack';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import RepositoryModal from 'Components/Pages/Setting/GitLabPage/RepositoryModal/RepositoryModal';
import GitData, { Git } from 'Components/MDClass/GitData/GitData';

interface GitProps {
  gitData: Git;
}
export default function GitLabRepositories({ gitData }: GitProps) {
  const [projectID, setProjectID] = useState('');
  const [repositoryURL, setRepositoryURL] = useState('');
  const [branchSpecifier, setBranchSpecifier] = useState('');
  const [credentials, setCredentials] = useState([]);
  const [open, setOpen] = useState(false);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleProjectIDChange = (event: any) => {
    setProjectID(event.target.value);
    gitData.projectID = event.target.value;
  };

  const handleRepositoryURLChange = (event: any) => {
    setRepositoryURL(event.target.value);
    gitData.repositoryurl = event.target.value;
  };

  const handleBranchSpecifierChange = (event: any) => {
    setBranchSpecifier(event.target.value);
    gitData.branchspecifier = event.target.value;
  };

  return (
    <Box my={3}>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper sx={{ padding: 1, textAlign: 'center', width: 170 }}>
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
                <SelectItem label="Credentials" Items={credentials} />
                <Button
                  variant="outlined"
                  startIcon={<AddIcon />}
                  size="small"
                  onClick={handleOpen}
                >
                  Add
                </Button>
                <RepositoryModal open={open} Close={handleClose} />
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
                defaultValue={branchSpecifier}
                onChange={handleBranchSpecifierChange}
              />
            </Grid>
          </Grid>
        </Paper>
      </Box>
    </Box>
  );
}
