import React, { useEffect } from 'react';
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepButton from '@mui/material/StepButton';
import Button from '@mui/material/Button';
import TabContext from '@mui/lab/TabContext';
import TabPanel from '@mui/lab/TabPanel';
import Paper from '@mui/material/Paper';
import axios from 'axios';

import { useParams } from 'react-router-dom';
import { ResponseIdName } from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';
import { useDropdownStore } from 'Components/Store/DropDownStore/DropDownStore';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';
import BuildData, { Build } from 'Components/MDClass/BuildData/BuildData';
import DBData, { DB } from 'Components/MDClass/DBData/DBData';
import GitData, { Git } from 'Components/MDClass/GitData/GitData';
import NginxData, { Nginx } from 'Components/MDClass/NginxData/NginxData';
import ProjectNamePaper from './BuildPage/ProjectNamePaper/ProjectNamePaper';
import NginxPage from './NginxPage/NginxPage';

import AxiosPage from './AxiosPage/AxiosPage';
import GitLabPage from './GitPage/GitPage';
import BuildPage from './BuildPage/BuildPage';
import DBpage from './DBpage/DBPage';

interface ProjectConfigInfo {
  buildConfigs: Build[];
  dbConfigs: DB[];
  gitConfig: Git;
  nginxConfig: Nginx;
  projectId: number;
  projectName: string;
}

const steps = [
  'FE/BE Settings',
  'DB Setting',
  'NginX Setting',
  'Git Setting',
  'Make Project',
];

export default function Setting() {
  const [activeStep, setActiveStep] = React.useState(0);
  const [completed, setCompleted] = React.useState<{
    [k: number]: boolean;
  }>({});

  const setProjectId = useSettingStore((state) => state.setProjectId);
  const setProjectName = useSettingStore((state) => state.setProjectName);
  const setBuildConfigs = useSettingStore((state) => state.setBuildConfigs);
  const setDBConfigs = useSettingStore((state) => state.setDBConfigs);
  const setGitConfig = useSettingStore((state) => state.setGitConfig);
  const setNginxConfig = useSettingStore((state) => state.setNginxConfig);

  const setFrameworkItems = useDropdownStore((state) => state.setFramwork);
  const setDBItems = useDropdownStore((state) => state.setDB);

  const params = useParams();

  const totalSteps = () => {
    return steps.length;
  };

  const completedSteps = () => {
    return Object.keys(completed).length;
  };

  const isLastStep = () => {
    return activeStep === totalSteps() - 1;
  };

  const allStepsCompleted = () => {
    return completedSteps() === totalSteps();
  };

  const handleNext = () => {
    const newActiveStep =
      isLastStep() && !allStepsCompleted()
        ? // It's the last step, but not all steps have been completed,
          // find the first step that has been completed
          steps.findIndex((step, i) => !(i in completed))
        : activeStep + 1;
    setActiveStep(newActiveStep);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  const handleStep = (step: number) => () => {
    setActiveStep(step);
  };

  // const handleComplete = () => {
  //   const newCompleted = completed;
  //   newCompleted[activeStep] = true;
  //   setCompleted(newCompleted);
  //   handleNext();
  // };

  const handleReset = () => {
    setActiveStep(0);
    setCompleted({});
  };

  useEffect(() => {
    const projectId = Object.values(params)[0];

    axios
      .get('/api/project/frameworkType')
      .then((res) => {
        const data = res.data as ResponseIdName[];
        setFrameworkItems([...data]);
      })
      .catch();

    axios.get('/api/project/dbType').then((res) => {
      setDBItems(res.data);
    });

    if (projectId === '0') return;

    axios.get(`/api/project/config/${projectId}`).then((res) => {
      const data = res.data as ProjectConfigInfo;

      if (data.projectId) setProjectId(data.projectId);
      if (data.projectName) setProjectName(data.projectName);
      if (data.buildConfigs) setBuildConfigs([...data.buildConfigs]);
      else setBuildConfigs([]);
      if (data.dbConfigs) setDBConfigs([...data.dbConfigs]);
      else setDBConfigs([]);
      if (data.gitConfig) setGitConfig(data.gitConfig);
      else setGitConfig(new GitData());
      if (data.nginxConfig) setNginxConfig(data.nginxConfig);
      else setNginxConfig(new NginxData());
    });
  }, []);

  return (
    <Box mt={3} sx={{ width: '100%' }}>
      <Paper sx={{ borderRadius: 3 }}>
        <Stepper
          nonLinear
          activeStep={activeStep}
          alternativeLabel
          sx={{ pt: 3 }}
        >
          {steps.map((label, index) => (
            <Step key={label} completed={completed[index]}>
              <StepButton color="inherit" onClick={handleStep(index)}>
                {label}
              </StepButton>
            </Step>
          ))}
        </Stepper>
        <Box>
          {allStepsCompleted() ? (
            <Box sx={{ display: 'flex', flexDirection: 'row', pt: 2 }}>
              <Box sx={{ flex: '1 1 auto' }} />
              <Button onClick={handleReset}>Reset</Button>
            </Box>
          ) : (
            <>
              <Box sx={{ display: 'flex', flexDirection: 'row', pt: 2 }}>
                <Button
                  color="inherit"
                  disabled={activeStep === 0}
                  onClick={handleBack}
                  sx={{ mr: 1 }}
                >
                  Back
                </Button>
                <Box sx={{ flex: '1 1 auto' }} />
                <Button onClick={handleNext} sx={{ mr: 1 }}>
                  Next
                </Button>
              </Box>
              <TabContext value={String(activeStep)}>
                <Box sx={{ mx: 3, borderBottom: 1, borderColor: 'divider' }} />
                <TabPanel value="0">
                  <ProjectNamePaper />
                  <BuildPage />
                </TabPanel>
                <TabPanel value="1">
                  <DBpage />
                </TabPanel>
                <TabPanel value="2">
                  <NginxPage />
                </TabPanel>
                <TabPanel value="3">
                  <GitLabPage />
                </TabPanel>
                <TabPanel value="4">
                  <AxiosPage />
                </TabPanel>
              </TabContext>
            </>
          )}
        </Box>
      </Paper>
    </Box>
  );
}
