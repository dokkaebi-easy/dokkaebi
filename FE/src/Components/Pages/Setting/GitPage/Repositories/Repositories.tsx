import { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import FormHelperText from '@mui/material/FormHelperText';
import axios from 'axios';
import { Git } from 'Components/MDClass/GitData/GitData';
import { useDropdownStore } from 'Components/Store/DropDownStore/DropDownStore';
import { ResponseIdName } from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';

interface GitProps {
  gitData: Git;
}

export default function GitLabRepositories({ gitData }: GitProps) {
  const [projectID, setProjectID] = useState(gitData.gitProjectId);
  const [repositoryURL, setRepositoryURL] = useState(gitData.repositoryUrl);
  const [branchName, setBranchName] = useState(gitData.branchName);

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
              <Typography>프로젝트 ID</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                label="Project ID"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="Project ID"
                defaultValue={projectID}
                onChange={handleProjectIDChange}
              />
              <FormHelperText id="component-helper-text">
                (※ 숫자만 기입 가능합니다.)
              </FormHelperText>
            </Grid>
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography>저장소 URL</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                label="Repository URL"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="https://lab.ssafy.com/**/**.git"
                defaultValue={repositoryURL}
                onChange={handleRepositoryURLChange}
              />
            </Grid>
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography>브랜치 명칭</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                label="Branch"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="ex) master"
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
