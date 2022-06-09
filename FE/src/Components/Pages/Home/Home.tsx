import Box from '@mui/material/Box';
import MDTable from 'Components/UI/Atoms/MDTable/MDTable';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import CreateIcon from '@mui/icons-material/Create';
import { Link } from 'react-router-dom';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';
import BuildData from 'Components/MDClass/BuildData/BuildData';
import GitData from 'Components/MDClass/GitData/GitData';
import NginxData from 'Components/MDClass/NginxData/NginxData';
import DBData from 'Components/MDClass/DBData/DBData';
import Cube from 'Components/UI/Atoms/Cube/Cube';
import { useEffect, useState } from 'react';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import { styled, useTheme } from '@mui/material/styles';

export default function Home() {
  const theme = useTheme();

  const cleanProjectId = useSettingStore((state) => state.setProjectId);
  const cleanProjecttName = useSettingStore((state) => state.setProjectName);
  const cleanBuildConfigs = useSettingStore((state) => state.setBuildConfigs);
  const cleanDBConfigs = useSettingStore((state) => state.setDBConfigs);
  const cleanGitConfig = useSettingStore((state) => state.setGitConfig);
  const cleanNginxConfig = useSettingStore((state) => state.setNginxConfig);

  const [hours, setHours] = useState(new Date().getHours());
  const [minutes, setMinutes] = useState(new Date().getMinutes());
  const [secondes, setSecondes] = useState(new Date().getSeconds());

  const handleCreateClick = () => {
    cleanProjectId(0);
    cleanProjecttName('');
    cleanBuildConfigs([]);
    cleanDBConfigs([]);
    cleanGitConfig(new GitData());
    cleanNginxConfig(new NginxData());
  };

  useEffect(() => {
    const clock = setInterval(() => {
      const timer = new Date();
      setHours(timer.getHours());
      setMinutes(timer.getMinutes());
      setSecondes(timer.getSeconds());
    }, 1000);

    return () => {
      clearInterval(clock);
    };
  }, []);

  return (
    <Box sx={{ marginTop: 5 }}>
      <Stack mt={2} direction="row" justifyContent="flex-end" spacing={2}>
        <Link to="/create" style={{ color: 'white', textDecoration: 'none' }}>
          <Button
            color="primary"
            variant="contained"
            endIcon={<CreateIcon />}
            onClick={handleCreateClick}
          >
            Create
          </Button>
        </Link>
      </Stack>
      <Box>
        <Box position="relative" sx={{ top: 20, left: 10 }}>
          <Paper
            sx={{
              padding: 1,
              textAlign: 'center',
              width: 200,
              background:
                theme.palette.mode === 'dark'
                  ? 'linear-gradient(45deg, #fff, #aaa)'
                  : 'linear-gradient(195deg, #666, #191919)',
              color: theme.palette.mode === 'dark' ? 'black' : 'white',
            }}
          >
            <Typography variant="h5">All Project</Typography>
          </Paper>
        </Box>
        <Paper sx={{ padding: 3, pt: 4 }} elevation={0}>
          <MDTable />
        </Paper>
      </Box>

      {/* <Stack mt={10} direction="row" justifyContent="space-evenly">
        <Cube time={hours} clock="h" color="20, 20, 20" />
        <Cube time={minutes} clock="m" color="100, 100, 100" />
        <Cube time={secondes} clock="s" color="40, 40, 40" />
      </Stack> */}
    </Box>
  );
}
