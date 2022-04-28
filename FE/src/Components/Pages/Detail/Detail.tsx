import React, { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import CircularProgressWithLabel from 'Components/UI/Atoms/CircularProgressWithLabel/CircularProgressWithLabel';
import Grid from '@mui/material/Grid';
import axios from 'axios';
import { useParams, useHistory } from 'react-router-dom';
import BuildStateData, {
  BuildState,
} from 'Components/MDClass/BuildStateData/BuildStateData';
import BuildStateBox from 'Components/Pages/Detail/BuildStateBox/BuildStateBox';
import { v4 as uuid } from 'uuid';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import { useStore } from 'Components/Store/SettingStore/SettingStore';
import BuildData, { Build } from 'Components/MDClass/BuildData/BuildData';
import GitData, { Git } from 'Components/MDClass/GitData/GitData';
import NginxData, { Nginx } from 'Components/MDClass/NginxData/NginxData';

interface ProjectConfigInfo {
  buildConfigs: Build[];
  gitConfig: Git;
  nginxConfig: Nginx;
  projectId: number;
  projectName: string;
}

export default function Detail() {
  const setProjectId = useStore((state) => state.setProjectId);
  const setProjectName = useStore((state) => state.setProjectName);
  const setBuildConfigs = useStore((state) => state.setBuildConfigs);
  const setGitConfig = useStore((state) => state.setGitConfig);
  const setNginxConfig = useStore((state) => state.setNginxConfig);

  const [buildStates, setBuildStates] = useState<BuildState[]>([]);
  const [progress, setProgress] = useState('진행중... (미완성)');
  const [pullColor, setPullColor] = useState(100);
  const [runColor, setRunColor] = useState(200);
  const [buildColor, setBuildColor] = useState(500);

  const history = useHistory();
  const params = useParams();

  const handleBackClick = () => {
    history.push(`/`);
  };

  const handleEditClick = () => {
    const projectId = Object.values(params)[0];
    axios.get(`/api/project/config/${projectId}`).then((res) => {
      const data = res.data as ProjectConfigInfo;
      console.log(data);

      if (data.projectId) setProjectId(data.projectId);
      if (data.projectName) setProjectName(data.projectName);
      if (data.buildConfigs) setBuildConfigs([...data.buildConfigs]);
      else setBuildConfigs([new BuildData()]);
      if (data.gitConfig) setGitConfig(data.gitConfig);
      else setGitConfig(new GitData());
      if (data.nginxConfig) setNginxConfig(data.nginxConfig);
      else setNginxConfig(new NginxData());
    });
    history.push(`/setting`);
  };

  useEffect(() => {
    axios
      .get('/api/project/build/total', { params })
      .then((res) => {
        const data = res.data as BuildState[];
        data.reverse();
        setBuildStates([...data]);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <Box
      sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
    >
      <Box sx={{ width: '90%', padding: 3 }}>
        <Stack mt={5} spacing={2} direction="row" justifyContent="flex-end">
          <Button
            variant="contained"
            onClick={handleEditClick}
            sx={{ background: 'linear-gradient(195deg, #ee6666, #ff2222)' }}
          >
            Del
          </Button>
          <Button
            variant="contained"
            onClick={handleEditClick}
            sx={{ background: 'linear-gradient(195deg, #777, #191919)' }}
          >
            Edit
          </Button>

          <Button
            variant="contained"
            onClick={handleBackClick}
            sx={{ background: 'linear-gradient(195deg, #777, #191919)' }}
          >
            Back
          </Button>
        </Stack>
        <Typography
          sx={{ textWeight: 4 }}
          my={4}
          variant="h2"
          textAlign="center"
        >
          Stage View
        </Typography>
        <Grid container spacing={2}>
          <Grid item xs={4}>
            <CircularProgressWithLabel
              value={progress}
              propsColor={pullColor}
            />
            <Typography
              sx={{ textWeight: 3 }}
              mt={2}
              align="center"
              variant="h4"
            >
              Git Pull
            </Typography>
          </Grid>
          <Grid item xs={4}>
            <CircularProgressWithLabel
              value={progress}
              propsColor={buildColor}
            />
            <Typography
              sx={{ textWeight: 3 }}
              mt={2}
              align="center"
              variant="h4"
            >
              Build
            </Typography>
          </Grid>
          <Grid item xs={4}>
            <CircularProgressWithLabel value={progress} propsColor={runColor} />
            <Typography
              sx={{ textWeight: 3 }}
              mt={2}
              align="center"
              variant="h4"
            >
              Run
            </Typography>
          </Grid>
        </Grid>
        <Box mt={10}>
          <Box sx={{ padding: 3 }}>
            <Grid container alignItems="flex-end">
              <Grid item xs={4}>
                <Typography sx={{ textWeight: 3 }} variant="h3">
                  Sate List
                </Typography>
              </Grid>
              <Grid item xs={8}>
                <Grid container justifyContent="center" alignItems="flex-end">
                  <Grid item xs={4}>
                    <Typography align="center" variant="h5">
                      Pull
                    </Typography>
                  </Grid>
                  <Grid item xs={4}>
                    <Typography align="center" variant="h5">
                      Build
                    </Typography>
                  </Grid>
                  <Grid item xs={4}>
                    <Typography align="center" variant="h5">
                      Run
                    </Typography>
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </Box>
          {buildStates.length ? (
            <>
              {buildStates.map((value) => {
                return <BuildStateBox key={uuid()} buildState={value} />;
              })}
            </>
          ) : (
            <Paper sx={{ backgroundColor: 'rgb(240, 240,240)', padding: 3 }}>
              <Typography align="center" variant="h6">
                빌드 해주세요...
              </Typography>
            </Paper>
          )}
        </Box>
      </Box>
    </Box>
  );
}
